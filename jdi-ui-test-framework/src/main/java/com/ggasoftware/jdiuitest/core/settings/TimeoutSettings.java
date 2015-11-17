/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
package com.ggasoftware.jdiuitest.core.settings;

import static com.ggasoftware.jdiuitest.core.asserter.BaseChecker.setDefaultTimeout;

/**
 * Created by 12345 on 04.07.2015.
 */
public class TimeoutSettings {
    public int currentTimeoutSec;

    public int waitElementSec = 20;
    public int waitPageLoadSec = 20;
    public int retryMSec = 100;

    public TimeoutSettings() {
        setCurrentTimeoutSec(20);
    }

    public void setCurrentTimeoutSec(int timeoutSec) {
        currentTimeoutSec = timeoutSec;
        setDefaultTimeout(timeoutSec * 1000L);
    }

    public void dropTimeouts() {
        setCurrentTimeoutSec(waitElementSec);
    }
}
