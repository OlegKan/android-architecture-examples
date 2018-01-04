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

package com.simplaapliko.architecture.blueprints.app.di.application;

import android.content.Context;

import com.simplaapliko.architecture.blueprints.app.di.screen.ScreenComponent;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = ScreenComponent.class)
public class AppModule {

    private final Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @ApplicationScope
    Context provideApplicationContext() {
        return mContext;
    }
}
