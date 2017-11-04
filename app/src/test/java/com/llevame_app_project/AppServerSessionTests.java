package com.llevame_app_project;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class AppServerSessionTests {
    @Test
    public void ASessionStartsWithoutAValidToken(){
        assertFalse(AppServerSession.thereIsAValidToken());
    }

    @Test
    public void afterSavingATokenThereIsAValidToken(){
        AppServerSession.thereIsAValidToken();
        assertFalse(AppServerSession.thereIsAValidToken());
    }
}
