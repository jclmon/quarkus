package org.acme.quickstart;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

public class WorldClockFallBack implements FallbackHandler<WorldClock> {

    @Override
    public WorldClock handle(ExecutionContext arg0) {
        WorldClock wc = new WorldClock();
        wc.setCurrentDateTime("No Time");
        return wc;
    }

}