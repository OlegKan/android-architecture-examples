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

package com.simplaapliko.architecture.blueprints.app.first;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.simplaapliko.architecture.R;
import com.simplaapliko.architecture.blueprints.app.BaseScreenComponentActivity;
import com.simplaapliko.architecture.blueprints.app.second.SecondActivity;

import javax.inject.Inject;

public class FirstActivity extends BaseScreenComponentActivity
        implements FirstActivityContract.MainView {

    @Inject FirstActivityContract.MainPresenter mPresenter;

    private static int sViewId;

    private TextView mPresenterHashCode;
    private TextView mViewHashCode;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, FirstActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sViewId++;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        bindViews();

        mPresenter.attachView(this);
    }

    private void bindViews() {
        mPresenterHashCode = findViewById(R.id.presenter_hash_code);
        mViewHashCode = findViewById(R.id.view_hash_code);

        findViewById(R.id.new_second_screen).setOnClickListener(
                v -> startActivity(SecondActivity.getStartIntent(FirstActivity.this)));
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void setupScreenComponent() {
        getScreenComponent().inject(this);
    }

    @Override
    public int getId() {
        return sViewId;
    }

    @Override
    public void render(@NonNull FirstModel model) {
        mPresenterHashCode.setText(model.getPresenterHashCode());
        mViewHashCode.setText(model.getViewHashCode());
    }
}
