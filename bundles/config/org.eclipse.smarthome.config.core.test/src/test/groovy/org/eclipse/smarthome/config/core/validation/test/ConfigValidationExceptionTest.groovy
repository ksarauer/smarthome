/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.config.core.validation.test

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*

import java.text.MessageFormat

import org.eclipse.smarthome.config.core.internal.validation.MessageKey
import org.eclipse.smarthome.config.core.validation.ConfigValidationException
import org.eclipse.smarthome.config.core.validation.ConfigValidationMessage
import org.eclipse.smarthome.core.i18n.TranslationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.osgi.framework.Bundle

/**
 * Testing the {@link ConfigValidationException}.
 *
 * @author Thomas Höfer - Initial contribution
 */
class ConfigValidationExceptionTest {

    private Locale defaultLocale

    private static final String PARAM1 = "param1"
    private static final String PARAM2 = "param2"

    private static final Locale DE = new Locale("de")
    private static final Locale EN = new Locale("en")

    private static final int MAX = 3
    private static final String TXT_DE1 = "German 1"
    private static final String TXT_DE2 = MessageFormat.format("German 2 with some {0} parameter", MAX)

    private static final String TXT_EN1 = "English 1"
    private static final String TXT_EN2 = MessageFormat.format("English 2 with some {0} parameter", MAX)

    private static final String TXT_DEFAULT1 = MessageKey.PARAMETER_REQUIRED.defaultMessage
    private static final String TXT_DEFAULT2 = MessageFormat.format(MessageKey.MAX_VALUE_TXT_VIOLATED.defaultMessage, MAX)

    private static final ConfigValidationMessage MSG1 = createMessage(PARAM1, TXT_DEFAULT1, MessageKey.PARAMETER_REQUIRED.key)
    private static final ConfigValidationMessage MSG2 = createMessage(PARAM2, TXT_DEFAULT2, MessageKey.MAX_VALUE_TXT_VIOLATED.key, [MAX])

    private static final List ALL = [
        MSG1,
        MSG2
    ]

    private static final Bundle bundle = [
        getBundleId: { -> 0 }
    ] as Bundle

    private TranslationProvider translationProvider;

    @Before
    void setUp() {
        translationProvider = [
            getText: { bundle, key, defaultText, locale, params ->
                if(locale == null) {
                    locale = Locale.ENGLISH;
                }
                if(MessageKey.PARAMETER_REQUIRED.key.equals(key)) {
                    if(DE.equals(locale)) {
                        TXT_DE1
                    } else {
                        TXT_EN1
                    }
                } else if(MessageKey.MAX_VALUE_TXT_VIOLATED.key.equals(key)) {
                    if(DE.equals(locale)) {
                        TXT_DE2
                    } else {
                        TXT_EN2
                    }
                }
            }
        ] as TranslationProvider
    }

    @After
    void tearDown(){
    }

    @Test
    void 'assert that default messages are provided'() {
        ConfigValidationException configValidationException = new ConfigValidationException(bundle, translationProvider, ALL)

        Map messages = configValidationException.getValidationMessages();

        assertThat messages.size(), is(2)
        assertThat messages.get(PARAM1), is(TXT_DEFAULT1)
        assertThat messages.get(PARAM2), is(TXT_DEFAULT2)
    }

    @Test
    void 'assert that internationalized messages are provided'() {
        ConfigValidationException configValidationException = new ConfigValidationException(bundle, translationProvider, ALL)

        Map messages = configValidationException.getValidationMessages(DE);

        assertThat messages.size(), is(2)
        assertThat messages.get(PARAM1), is(TXT_DE1)
        assertThat messages.get(PARAM2), is(TXT_DE2)

        messages = configValidationException.getValidationMessages(EN)

        assertThat messages.size(), is(2)
        assertThat messages.get(PARAM1), is(TXT_EN1)
        assertThat messages.get(PARAM2), is(TXT_EN2)
    }

    @Test
    void 'assert that default messages are provided if no i18n provider is available'() {
        ConfigValidationException configValidationException = new ConfigValidationException(bundle, null, ALL)

        Map messages = configValidationException.getValidationMessages(DE);

        assertThat messages.size(), is(2)
        assertThat messages.get(PARAM1), is(TXT_DEFAULT1)
        assertThat messages.get(PARAM2), is(TXT_DEFAULT2)

        messages = configValidationException.getValidationMessages(EN)

        assertThat messages.size(), is(2)
        assertThat messages.get(PARAM1), is(TXT_DEFAULT1)
        assertThat messages.get(PARAM2), is(TXT_DEFAULT2)
    }

    @Test(expected=NullPointerException)
    void 'assert that NPE is thrown for null config validation messages'() {
        new ConfigValidationException(bundle, translationProvider, null)
    }

    def static ConfigValidationMessage createMessage(String parameterName, String defaultMessage, String messageKey, Collection<Object> content=Collections.<String> emptyList()) {
        return new ConfigValidationMessage(parameterName, defaultMessage, messageKey, content)
    }
}
