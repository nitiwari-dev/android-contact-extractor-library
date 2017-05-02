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

package com.bbmyjio.contactextractor.contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class ContactsInfo
{
    private String identity;

    private CPhone phone;

    private Postal postal;

    private Email email;

    private Events events;

    private Oraganisation oraganisation;

    private Name name;

    private String relation;

    private Account account;

    private String favorites;

    private Groups[] groups;

    public String getIdentity ()
    {
        return identity;
    }

    public void setIdentity (String identity)
    {
        this.identity = identity;
    }

    public CPhone getPhone ()
    {
        return phone;
    }

    public void setPhone (CPhone phone)
    {
        this.phone = phone;
    }

    public Postal getPostal ()
    {
        return postal;
    }

    public void setPostal (Postal postal)
    {
        this.postal = postal;
    }

    public Email getEmail ()
    {
        return email;
    }

    public void setEmail (Email email)
    {
        this.email = email;
    }

    public Events getEvents ()
    {
        return events;
    }

    public void setEvents (Events events)
    {
        this.events = events;
    }

    public Oraganisation getOraganisation ()
    {
        return oraganisation;
    }

    public void setOraganisation (Oraganisation oraganisation)
    {
        this.oraganisation = oraganisation;
    }

    public Name getName ()
    {
        return name;
    }

    public void setName (Name name)
    {
        this.name = name;
    }

    public String getRelation ()
    {
        return relation;
    }

    public void setRelation (String relation)
    {
        this.relation = relation;
    }

    public Account getAccount ()
    {
        return account;
    }

    public void setAccount (Account account)
    {
        this.account = account;
    }

    public String getFavorites ()
    {
        return favorites;
    }

    public void setFavorites (String favorites)
    {
        this.favorites = favorites;
    }

    public Groups[] getGroups ()
    {
        return groups;
    }

    public void setGroups (Groups[] groups)
    {
        this.groups = groups;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [identity = "+identity+", phone = "+phone+", postal = "+postal+", email = "+email+", events = "+events+", oraganisation = "+oraganisation+", name = "+name+", relation = "+relation+", account = "+account+", favorites = "+favorites+", groups = "+groups+"]";
    }
}
