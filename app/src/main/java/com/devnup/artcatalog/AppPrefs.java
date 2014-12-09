package com.devnup.artcatalog;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.util.Date;

/**
 * Created by luiseduardobrito on 12/7/14.
 */
public interface AppPrefs {

    public Date installedAt();

    public Date lastOpenedAt();
}
