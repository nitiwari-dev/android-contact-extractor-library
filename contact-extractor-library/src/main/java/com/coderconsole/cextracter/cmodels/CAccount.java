/*
 *   Copyright (C) 2017 Nitesh Tiwari.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.coderconsole.cextracter.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact Account Information
 *
 * Created by Nitesh on 19-04-2017.
 */

public class CAccount  {
    private List<ContactGenericType> mGenericType;

    public List<ContactGenericType> getmGenericType() {
        return mGenericType;
    }

    public void setmGenericType(List<ContactGenericType> mGenericType) {
        this.mGenericType = new ArrayList<>(mGenericType);
    }
}
