package com.comsystoreply.labs.stationfinder.adapters.job;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driving.*;

import java.util.concurrent.*;

public class ImportStationsJob {
    private final ForAutomatedProcessesOfApplication port;
    private final User.SystemUser systemUser;
    private final ExecutorService executor;

    public ImportStationsJob(ForAutomatedProcessesOfApplication port, User.SystemUser systemUser) {
        this.port = port;
        this.systemUser = systemUser;
        this.executor = Executors.newFixedThreadPool(1);
    }

    public void runOnceImmediately() {
        executor.submit(() -> port.importCurrentStations(systemUser));
    }
}
