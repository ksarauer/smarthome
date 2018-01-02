/**
 * Copyright (c) 2014,2017 Contributors to the Eclipse Foundation
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
package org.eclipse.smarthome.binding.digitalstrom.internal.lib.event.constants;

import java.util.HashMap;

/**
 * The {@link EventResponseEnum} contains digitalSTROM-Event properties of the events at {@link EventNames}.
 *
 * @author Michael Ochel
 * @author Mathias Siegele
 */
public enum EventResponseEnum {

    // general
    NAME("Name"),
    PROPERTIES("properties"),
    SOURCE("source"),
    SET("set"),
    DSID("dsid"),
    ZONEID("zoneID"),
    GROUPID("groupID"),
    IS_APARTMENT("isApartment"),
    IS_GROUP("isGroup"),
    IS_DEVICE("isDevice"),
    IS_SERVICE("isService"),

    // scene event
    FORCED("forced"),
    ORIGEN_TOKEN("originToken"),
    CALL_ORIGEN("callOrigin"),
    ORIGEN_DSUID("originDSUID"),
    SCENEID("sceneID"),
    ORIGIN_DEVICEID("originDeviceID"),

    // device/zone sensor value
    SENSOR_VALUE_FLOAT("sensorValueFloat"),
    SENSOR_TYPE("sensorType"),
    SENSOR_VALUE("sensorValue"),
    SENSOR_INDEX("sensorIndex"),

    // state changed
    OLD_VALUE("oldvalue"),
    STATE_NAME("statename"),
    STATE("state"),
    VALUE("value"),

    // operation mode
    ACTIONS("actions"),
    OPERATION_MODE("operationMode"),
    FORCED_UPDATE("forceUpdate"),

    // binary input
    INPUT_TYPE("inputType"),
    INPUT_STATE("inputState"),
    INPUT_INDEX("inputIndex");

    private final String id;
    static final HashMap<String, EventResponseEnum> eventResponseFields = new HashMap<String, EventResponseEnum>();

    static {
        for (EventResponseEnum ev : EventResponseEnum.values()) {
            eventResponseFields.put(ev.getId(), ev);
        }
    }

    /**
     * Returns true, if the given property exists at the ESH event properties, otherwise false.
     *
     * @param property to check
     * @return contains property (true = yes | false = no)
     */
    public static boolean containsId(String property) {
        return eventResponseFields.keySet().contains(property);
    }

    /**
     * Returns the {@link EventResponseEnum} to the given property.
     *
     * @param property to get
     * @return EventPropertyEnum
     */
    public static EventResponseEnum getProperty(String property) {
        return eventResponseFields.get(property);
    }

    private EventResponseEnum(String id) {
        this.id = id;
    }

    /**
     * Returns the id of this {@link EventResponseEnum}.
     *
     * @return id of this {@link EventResponseEnum}
     */
    public String getId() {
        return id;
    }
}