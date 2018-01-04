/*
 * Copyright (C) 2018 Oleg Kan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplaapliko.architecture.blueprints.app;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.simplaapliko.architecture.blueprints.app.di.application.AppComponent;
import com.simplaapliko.architecture.blueprints.app.di.screen.ScreenComponent;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseScreenComponentActivity extends AppCompatActivity {

    private static final String BUNDLE_SCREEN_SESSION_ID = "screenSessionId";

    private static final Map<String, ScreenComponent> SCREEN_COMPONENT_CACHE = new HashMap<>();

    private static int sScreenSessionCounter;
    private int mScreenSessionId;

    static AppComponent getAppComponent(Context context) {
        Application application = (Application) context.getApplicationContext();
        return ((App) application).getAppComponent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mScreenSessionId = savedInstanceState.getInt(BUNDLE_SCREEN_SESSION_ID, 1);
        } else {
            sScreenSessionCounter++;
            mScreenSessionId = sScreenSessionCounter;
        }

        setupScreenComponent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_SCREEN_SESSION_ID, mScreenSessionId);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearScreenComponent();
    }

    protected ScreenComponent getScreenComponent() {
        String screenId = getScreenId();
        ScreenComponent screenComponent = SCREEN_COMPONENT_CACHE.get(screenId);
        if (screenComponent == null) {
            screenComponent = getAppComponent(this).screenComponentBuilder().build();
            SCREEN_COMPONENT_CACHE.put(screenId, screenComponent);
        }
        return screenComponent;
    }

    private void clearScreenComponent() {
        SCREEN_COMPONENT_CACHE.remove(getScreenId());
    }

    private String getScreenId() {
        return getClass().getSimpleName() + mScreenSessionId;
    }

    protected abstract void setupScreenComponent();
}
