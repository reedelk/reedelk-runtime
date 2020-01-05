package com.reedelk.runtime.adminconsole;

import com.reedelk.runtime.Application;
import com.reedelk.runtime.commons.ApplicationTask;

public class AdminConsoleInstallTask extends ApplicationTask {

    public static void execute(Application application) {
        new Thread(new AdminConsoleInstallTask(application)).start();
    }

    private AdminConsoleInstallTask(Application application) {
        super(application);
    }

    @Override
    protected void doRun() {
        // Async, runtime-admin console is not mandatory.
        // We prefer to install the runtime-admin console later, so that
        // the runtime can start faster.
        application.installAdminConsole();
    }
}
