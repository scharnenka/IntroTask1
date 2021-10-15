package driverManger;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import org.openqa.selenium.WebDriver;

public class DriverModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(WebDriver.class)
                .to(DriverProvider.class)
                .in(Scopes.SINGLETON);
    }
}
