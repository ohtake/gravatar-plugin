/*
 * The MIT License
 * 
 * Copyright (c) 2011, Erik Ramfelt
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jenkinsci.plugins.gravatar;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class UserGravatarResolverTest {
    
    private GravatarImageURLVerifier urlVerifier;

    @Before
    public void setUp() {
        urlVerifier = mock(GravatarImageURLVerifier.class);
    }

    @Test
    public void assertResolverVerifiesThatGravatarExists() {
        UserGravatarResolver resolver = new UserGravatarResolver(urlVerifier);
        when(urlVerifier.verify(anyString())).thenReturn(Boolean.TRUE);
        assertThat(resolver.checkIfGravatarExistsFor("eramfelt@gmail.com"), is(true));
    }

    @Test
    public void assertResolverReturnsNullForNonExistingGravatar() {
        UserGravatarResolver resolver = new UserGravatarResolver(urlVerifier);
        when(urlVerifier.verify(anyString())).thenReturn(Boolean.FALSE);
        assertThat(resolver.checkIfGravatarExistsFor("eramfelt@gmail.com"), is(false));
    }

    @Test
    public void assertResolverOnlyVerifiesAnImageUrlOnce() {
        UserGravatarResolver resolver = new UserGravatarResolver(urlVerifier);
        when(urlVerifier.verify(anyString())).thenReturn(Boolean.TRUE);
        resolver.checkIfGravatarExistsFor("eramfelt@gmail.com");
        resolver.checkIfGravatarExistsFor("eramfelt@gmail.com");
        resolver.checkIfGravatarExistsFor("eramfelt@gmail.com");
        verify(urlVerifier, times(1)).verify("eramfelt@gmail.com");
    }
}
