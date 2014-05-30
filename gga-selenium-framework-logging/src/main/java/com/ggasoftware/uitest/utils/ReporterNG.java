/****************************************************************************
 * Copyright (C) 2014 GGA Software Services LLC
 *
 * This file may be distributed and/or modified under the terms of the
 * GNU General Public License version 3 as published by the Free Software
 * Foundation.
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 ***************************************************************************/
package com.ggasoftware.uitest.utils;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class ReporterNG {

    public static final char BUSINESS_LEVEL = '2';
    public static final char COMPONENT_LEVEL = '1';
    public static final char TECHNICAL_LEVEL = '0';

    private static final char PASS = '0';
    private static final char WARNING = '1';
    private static final char FAIL = '2';

    public static final String PASSED = "Passed";
    public static final String FAILED = "Failed";


    public static Logger LOG = Logger.getLogger(ReporterNG.class.getSimpleName());
    private static final String SDFP = new SimpleDateFormat("HH:mm:ss.SSS").toPattern();

    /**
     * Adds message to business part of test LOG. Must only be used on Test level
     * @param message the message to add
     */
    public static void logBusiness(String message) {
        Reporter.log(BUSINESS_LEVEL + " " + DateUtil.now(SDFP) + "~ " + message);
        LOG.info(message);
    }

    /**
     * Adds message to technical part of test LOG. Must only be used on Control level
     * @param message the message to add
     */
    public static void logTechnical(String message) {
        Reporter.log(TECHNICAL_LEVEL + " " + message);
        LOG.info(message);
    }

    /**
     * Adds message to component part of test LOG. Must only be used on Component level
     * @param message the message to add
     */
    public static void logComponent(String message) {
        Reporter.log(COMPONENT_LEVEL + " " + message);
        LOG.info(message);
    }

    /**
     * Adds warning message to business part of test LOG. Must only be used on Test level
     * @param message the message to add
     */
    public static void logWarning(String message) {
        Reporter.log(ReporterNG.BUSINESS_LEVEL + " " + message);
        LOG.warn(message);
    }

    /**
     * Adds failed message to selected part of test LOG.
     * @param logLevel - ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param message the message to add
     */
    public static void logFailed(char logLevel, String message) {
        Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + ".");
        LOG.warn(message + ": " + FAILED);
        TestBase.setFailed(message);
    }

    /**
     * Checks whether text matches regular expression is TRUE. In case of matches is FALSE marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel      ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param value - actual object value
     * @param regExp - regular expression to matches
     * @param message the message to add in case of inequality
     */
    public static String logAssertMatch(char logLevel, String value, String regExp, String message) {
        if (!value.matches(regExp)) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> Expected: \"" + regExp + "\".<br> Actual: \"" + value + "\"");
            LOG.warn(message + ": " + FAILED + "." + "<br> Expected: \"" + regExp + "\".<br> Actual: \"" + value + "\"");
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + "." + "<br> Expected: \"" + regExp + "\"");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Checks whether arrays not intersect. In case of arrays intersected marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel      ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param firstArray - first array of string
     * @param secondArray - second array of string
     * @param message the message to add in case of inequality
     */
    public static String logAssertNotIntersect(char logLevel, String[] firstArray, String[] secondArray, String message) {
        ArrayList<String> secondArrayList = new ArrayList<>();
        ArrayList<String> firstArrayList = new ArrayList<>();
        Collections.addAll(secondArrayList, secondArray);
        Collections.addAll(firstArrayList, firstArray);
        if (firstArrayList.removeAll(secondArrayList)) {
            Collections.addAll(firstArrayList, firstArray);
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> First: " + firstArrayList.toString() + "<br> Second: " + secondArrayList.toString());
            LOG.warn(message + ": " + FAILED + "." + "<br> First: " + firstArrayList.toString() + "<br> Second: " + secondArrayList.toString());
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + ".");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Compares two Objects as strings. In case of inequality marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel      ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param value - actual object value
     * @param expectedValue - expected object value
     * @param message       the message to add in case of inequality
     */
    public static String logAssertEquals(char logLevel, Object value, Object expectedValue, String message) {
        if (value == null || !value.equals(expectedValue)) {
            if (value == null) {
                Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> Actual: \"NULL\"");
                LOG.warn(message + ": " + FAILED + "." + "<br> Actual: \"NULL\"");
            } else {
                Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> Expected: \"" + expectedValue.toString() + "\". <br> Actual: \"" + value.toString() + "\"");
                LOG.warn(message + ": " + FAILED + "." + "<br> Expected: \"" + expectedValue.toString() + "\". <br> Actual: \"" + value.toString() + "\"");
            }
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + "." + "<br> Expected: \"" + expectedValue.toString() + "\"");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Compares two Objects as strings. In case of inequality marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel      ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param value - actual object value
     * @param notExpectedValue - not expected object value
     * @param message       the message to add in case of inequality
     */
    public static String logAssertNotEquals(char logLevel, Object value, Object notExpectedValue, String message) {
        if (value == null || value.equals(notExpectedValue)) {
            if (value == null) {
                Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> Actual: \"NULL\"");
                LOG.warn(message + ": " + FAILED + "." + "<br> Actual: \"NULL\"");
            } else {
                Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> Not expected: \"" + notExpectedValue.toString() + "\"");
                LOG.warn(message + ": " + FAILED + "." + "<br> Not expected: \"" + notExpectedValue.toString() + "\"");
            }
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + "." + "<br> Not Expected: \"" + notExpectedValue.toString() + "\".<br> Actual: \"" + value.toString());
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Compares two String arrays. In case of inequality marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel      ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param value - actual object value
     * @param expectedValue - expected object value
     * @param message       the message to add in case of inequality
     */
    public static String logAssertEquals(char logLevel, String[] value, String[] expectedValue, String message) {
        boolean misMatch = false;
        if (value.length != expectedValue.length) {
            misMatch = true;
        } else {
            for (int j = 0; j < value.length; j++) {
                if (!value[j].equalsIgnoreCase(expectedValue[j])) {
                    misMatch = true;
                    break;
                }
            }
        }
        if (misMatch) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> Expected: " + stringArrayToString(expectedValue) + ".<br> Actual: " + stringArrayToString(value) + "");
            LOG.warn(message + ": " + FAILED + "." + "<br> Expected: " + stringArrayToString(expectedValue) + ".<br> Actual: " + stringArrayToString(value) + "");
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + "." + "<br> Expected: " + stringArrayToString(expectedValue) + "");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    private static String stringArrayToString(String[] sa) {
        StringBuilder sb = new StringBuilder("{");
        for (String aSa : sa) {
            sb.append(" ")
                    .append("\"")
                    .append(aSa)
                    .append("\"");
        }
        sb.append(" }");
        return sb.toString();
    }

    /**
     * Checks whether logical expression is TRUE. In case of logical expression is FALSE marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param what     logical expression that should be TRUE
     * @param message  the message to add in case of logical expression is FALSE
     */
    public static String logAssertTrue(char logLevel, boolean what, String message) {
        if (!what) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + ".<br> Expected: true. Actual: false");
            LOG.warn(message + ": " + FAILED + ".<br> Expected: true. Actual: false");
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + ".");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Checks whether logical expression is FALSE. In case of logical expression is TRUE marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param what     logical expression that should be FALSE
     * @param message  the message to add in case of logical expression is TRUE
     */
    public static String logAssertFalse(char logLevel, boolean what, String message) {
        String status;
        if (what) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + ".<br> Expected. false. Actual: true");
            LOG.warn(message + ": " + FAILED + ".<br> Expected. false. Actual: true");
            status = FAILED;
            TestBase.setFailed(message);
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + ".");
            LOG.info(message + ": " + PASSED);
            status = PASSED;
        }
        return status;
    }

    /**
     * Checks whether text is empty. In case of text not empty marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param what     logical expression that should be FALSE
     * @param message  the message to add in case of logical expression is TRUE
     */
    public static String logAssertEmpty(char logLevel, String what, String message) {
        if (!what.isEmpty()) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + ".<br> Expected: Empty.<br> Actual: \"" + what + "\"");
            LOG.warn(message + ": " + FAILED + ".<br> Expected: Empty.<br> Actual: \"" + what + "\"");
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + ".<br> Expected: Empty.");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Checks whether one string contains another. In case of check failure marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel     ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param toSearchIn   the string to search in
     * @param whatToSearch the string to search
     * @param message      the message to add in case of check failure
     */
    public static String logAssertContains(char logLevel, String toSearchIn, String whatToSearch, String message) {
        if (!toSearchIn.contains(whatToSearch)) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br>\"" + toSearchIn + "\"<br> doesn't contain: \"" + whatToSearch + "\"");
            LOG.warn(message + ": " + FAILED + "." + "<br>\"" + toSearchIn + "\"<br> doesn't contain: \"" + whatToSearch + "\"");
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + "." + "<br>\"" + whatToSearch + "\"<br> contains in:  \"" + toSearchIn + "\"");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Checks whether one string not contains another. In case of check failure marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel     ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param toSearchIn   the string to search in
     * @param whatToSearch the string to search
     * @param message      the message to add in case of check failure
     */
    public static String logAssertNotContains(char logLevel, String toSearchIn, String whatToSearch, String message) {
        if (toSearchIn.contains(whatToSearch)) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br>\"" + toSearchIn + "\"<br> contains in: \"" + whatToSearch + "\"");
            LOG.warn(message + ": " + FAILED + "." + "<br>\"" + toSearchIn + "\"<br> contains in: \"" + whatToSearch + "\"");
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + "." + "<br>\"" + whatToSearch + "\"<br> doesn't contain:  \"" + toSearchIn + "\"");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Checks whether Object is NULL. In case if Object is NOT NULL marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param what     Object to check for NULL
     * @param message  the message to add in case of check failure
     */
    public static String logAssertNull(char logLevel, Object what, String message) {
        if (what != null) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> Must be NULL<br> but: \"" + what.toString() + "\"");
            LOG.warn(message + ": " + FAILED + "." + "<br> Must be NULL<br> but: \"" + what.toString() + "\"");
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + ". Value is null");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Checks whether Object is NULL. In case if Object is NOT NULL marks test as FAILED. Execution continuous.
     * Depending on logLevel param adds message to business, component or technical level
     *
     * @param logLevel ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param what     Object to check for NULL
     * @param message  the message to add in case of check failure
     */
    public static String logAssertNotNull(char logLevel, Object what, String message) {
        if (what == null) {
            Reporter.log(logLevel + String.valueOf(FAIL) + DateUtil.now(SDFP) + "~ " + message + ": " + FAILED + "." + "<br> Must not be NULL");
            LOG.warn(message + ": " + FAILED + "." + "<br> Must not be NULL");
            TestBase.setFailed(message);
            return FAILED;
        } else {
            Reporter.log(logLevel + String.valueOf(PASS) + DateUtil.now(SDFP) + "~ " + message + ": " + PASSED + ". Value is \"" + what.toString() + "\"");
            LOG.info(message + ": " + PASSED);
            return PASSED;
        }
    }

    /**
     * Log getting parameter value from Element.
     *
     * @param logLevel ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param elementName - element to get value
     * @param parameterName - name of parameter to get value
     * @param value - object to get value
     */
    public static void logGetter(char logLevel, String elementName, String parameterName, Object value) {
        Reporter.log(logLevel + " " + "Get " + parameterName + " value at " + elementName + ", value = " + value);
        LOG.info("Get " + parameterName + " value at " + elementName + ", value = " + value);
    }

    /**
     * Log setting parameter value to Element.
     *
     * @param logLevel ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param elementName - element to set value
     * @param parameterName - name of parameter to set value
     * @param value - object to set value
     */
    public static void logSetter(char logLevel, String elementName, String parameterName, Object value) {
        Reporter.log(logLevel + " " + "Setting " + parameterName + " at " + elementName + " = " + value);
        LOG.info("Setting " + parameterName + " at " + elementName + " = " + value);
    }

    /**
     * Log action name.
     *
     * @param logLevel ReporterNG.BUSINESS_LEVEL, ReporterNG.COMPONENT_LEVEL or ReporterNG.TECHNICAL_LEVEL
     * @param elementName - element to perform action
     * @param actionName - action name
     */
    public static void logAction(char logLevel, String elementName, String actionName) {
        Reporter.log(logLevel + " " + "Perform " + actionName + " at " + elementName);
        LOG.info("Perform " + actionName + " at " + elementName);
    }

    private static void logBusiness(String message, char status) {
        Reporter.log(BUSINESS_LEVEL + String.valueOf(status) + message);
        if (FAIL == status) {
            LOG.warn(message);
        } else {
            LOG.info(message);
        }
    }

    private static void logComponent(String message, char status) {
        Reporter.log(COMPONENT_LEVEL + status + message);
        if (FAIL == status) {
            LOG.warn(message);
        } else {
            LOG.info(message);
        }
    }

    public static void log4j(String message) {
        LOG.info(message);
    }
    public static void log4jError(String message) {
        LOG.error(message);
    }

    public static void setAttribute(String attribute, Object value) {
        if (Reporter.getCurrentTestResult() == null){
            return;
        }
        Reporter.getCurrentTestResult().setAttribute(attribute, value);
    }

    public static Object getAttribute(String attribute) {
        if (Reporter.getCurrentTestResult() == null){
            return null;
        }
        return Reporter.getCurrentTestResult().getAttribute(attribute);
    }

    public static String getLastAttributeName() {
        Set<String> attributeNames = Reporter.getCurrentTestResult().getAttributeNames();
        return attributeNames.toArray(new String[attributeNames.size()])[attributeNames.size() - 1];
    }

}
