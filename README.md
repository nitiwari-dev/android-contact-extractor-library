# android-contact-extractor

Extract all the contacts from android 'Contacts' application by providing simple easy-to-use apis. It helps to remove the overhead with some messy contacts content provider query.

Name             |  Phone
:-------------------------:|:-------------------------:
![name](https://cloud.githubusercontent.com/assets/10304040/25949742/35328eec-3676-11e7-84b2-a864b30269ea.png) | ![phone](https://cloud.githubusercontent.com/assets/10304040/25949859/a4cb69f4-3676-11e7-96bf-8231f3694792.png)

## Few Examples:

###### Extract all the PHONE - Its contains home/work/phone/other contacts within as ``HashSet<String>``

        CQuery cQuery = CQuery.getInstance(getActivity());
        cQuery.filter(ICFilter.ONLY_PHONE);
        cQuery.build(new IContact() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                if (cList != null && cList.size() > 0) {
                    CPhone cPhone = cList.getcPhone();
                    HashSet<String> home = cPhone.getHome();
                    HashSet<String> work = cPhone.getWork();
                    HashSet<String> mobile = cPhone.getMobile();
                    HashSet<String> other = cPhone.getOther();
                }

            }

            @Override
            public void onContactError(Throwable throwable) {

            }
        });
        
###### Extract all the Email - It contains home/work/phone/other contacts as simple ``HashSet<String>``

        CQuery cQuery = CQuery.getInstance(getActivity());
        cQuery.filter(ICFilter.ONLY_EMAIL);
        cQuery.build(new IContact() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                if (cList != null && cList.size() > 0) {
                      CEmail cEmail = cList.getcEmail();
                      HashSet<String> home = cEmail.getHome();
                      HashSet<String> work = cEmail.getWork();
                      HashSet<String> mobile = cEmail.getMobile();
                      HashSet<String> other = cEmail.getOther();
                }

            }

            @Override
            public void onContactError(Throwable throwable) {

            }
        });
        
###### Extract all the Account - It name and its type e.g name - ``nitiwari.dev@gmail.com/ type - com.google.com``

        CQuery cQuery = CQuery.getInstance(getActivity());
        cQuery.filter(ICFilter.ONLY_ACCOUNT);
        cQuery.build(new IContact() {
            @Override
            public void onContactSuccess(List<CList> mList) {
                if (cList != null && cList.size() > 0) {
                       CAccount cAccount = cList.getcAccount();
                }

            }

            @Override
            public void onContactError(Throwable throwable) {

            }
        });

Bingo Thats it !!!. Just change filter types and get the contact information accordingly. 

## Licence
        Copyright (C) 2017 Nitesh Tiwari.
  
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
 
                http://www.apache.org/licenses/LICENSE-2.0
 
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
