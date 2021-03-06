/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cxf.rs.security.jose.jws;

import org.apache.cxf.rs.security.jose.JoseHeaders;


public abstract class AbstractJwsSignatureProvider implements JwsSignatureProvider {
    private String algorithm;
    
    protected AbstractJwsSignatureProvider(String algo) {
        this.algorithm = algo;
    }
    
    protected JoseHeaders prepareHeaders(JoseHeaders headers) {
        if (headers == null) {
            headers = new JoseHeaders();
        }
        String algo = headers.getAlgorithm();
        if (algo != null) {
            checkAlgorithm(algo);
        } else {
            checkAlgorithm(algorithm);
            headers.setAlgorithm(algorithm);
        }
        return headers;
    }
    @Override
    public String getAlgorithm() {
        return algorithm;    
    }
    @Override
    public byte[] sign(JoseHeaders headers, byte[] content) {
        JwsSignature sig = createJwsSignature(headers);
        sig.update(content, 0, content.length);
        return sig.sign();
    }
    @Override
    public JwsSignature createJwsSignature(JoseHeaders headers) {
        return doCreateJwsSignature(prepareHeaders(headers));
    }
    
    protected abstract JwsSignature doCreateJwsSignature(JoseHeaders headers);
    
    protected void checkAlgorithm(String algo) {
        if (algo == null) {
            throw new SecurityException();
        }
    }

}
