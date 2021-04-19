package com.garrett.wiredgamble.models;

public enum PlacedBetStatus {
    ACTIVE(0),
    COMPLETED_WON(1),
    COMPLETED_LOST(2),
    FAILED(-1);

    private final int mStatus;

    PlacedBetStatus(int status) {
        mStatus = status;
    }

    public int getStatus() {
        return mStatus;
    }
}
