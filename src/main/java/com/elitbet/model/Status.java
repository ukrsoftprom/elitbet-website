package com.elitbet.model;

public enum Status{
    EVENT_NOT_STARTED("Not started"), EVENT_STARTED("Started"), EVENT_POSTPONED("Postponed"),
    EVENT_FINISHED("Finished"), EVENT_TYPE_FOOTBALL_MATCH("Football Match"), OUTCOME_NO_STATUS("No status"),
    OUTCOME_PASSED("Passed"), OUTCOME_NOT_PASSED("Not Passed"), OUTCOME_RETURNED("Returned"),
    OUTCOME_TYPE_FIRST_WIN("1"), OUTCOME_TYPE_SECOND_WIN("2"), OUTCOME_TYPE_DRAW("X"), WAGER_NO_STATUS("No status"),
    WAGER_PASSED("Passed"), WAGER_NOT_PASSED("Not passed"), WAGER_RETURNED("Returned");

    Status(String nameInDatabase) {
    }
}
