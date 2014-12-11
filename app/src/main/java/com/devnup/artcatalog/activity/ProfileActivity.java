package com.devnup.artcatalog.activity;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.base.BaseProfileActivity;
import com.devnup.artcatalog.activity.profile.ProfileInfoData;
import com.devnup.artcatalog.activity.profile.data.ArtFormProfileInfoData;
import com.devnup.artcatalog.activity.profile.data.ArtPeriodProfileInfoData;
import com.devnup.artcatalog.activity.profile.data.ArtistProfileInfoData;
import com.devnup.artcatalog.activity.profile.data.ArtworkProfileInfoData;
import com.devnup.artcatalog.ws.model.BaseModel;
import com.devnup.artcatalog.ws.model.VisualArtFormModel;
import com.devnup.artcatalog.ws.model.VisualArtPeriodModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;
import com.devnup.artcatalog.ws.service.ArtRestService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author luiseduardobrito
 * @since 12/11/14.
 */
@EActivity(R.layout.activity_profile)
public class ProfileActivity extends BaseProfileActivity {

    @Extra
    String mid;

    @Extra
    Type type;

    @Bean
    ArtRestService rest;

    ProfileInfoData data;

    BaseModel model;

    @Override
    @Background
    protected void getDataForInit(String mid) {
        notifyDataForInit(type.getRequest().getData(rest, this.mid));
    }

    @Override
    protected ProfileInfoData getData() {
        return data;
    }

    @Override
    protected String getMid() {
        return mid;
    }

    @UiThread
    protected void notifyDataForInit(BaseModel model) {

        mProgressBar.setVisibility(View.GONE);

        if (model != null) {

            this.model = model;

            try {

                Class[] argTypes = {Context.class, type.getModelClass()};
                Constructor constructor = type.getDataClass().getDeclaredConstructor(argTypes);
                Object[] arguments = {this, model};
                data = (ProfileInfoData) constructor.newInstance(arguments);

            } catch (InstantiationException e) {
                e.printStackTrace();
                Log.e(getClass().getSimpleName(), e.getStackTrace().toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e(getClass().getSimpleName(), e.getStackTrace().toString());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                Log.e(getClass().getSimpleName(), e.getStackTrace().toString());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                Log.e(getClass().getSimpleName(), e.getStackTrace().toString());
            }

            notifyDataIsReady();
        }
    }

    public enum Type implements Serializable {

        ARTIST(VisualArtistModel.class, ArtistProfileInfoData.class, new RequestInterface() {

            @Override
            public BaseModel getData(ArtRestService rest, String mid) {
                return rest.getVisualArtist(mid);
            }

        }),
        ARTWORK(VisualArtworkModel.class, ArtworkProfileInfoData.class, new RequestInterface() {

            @Override
            public BaseModel getData(ArtRestService rest, String mid) {
                return rest.getVisualArtwork(mid);
            }

        }),
        ART_PERIOD(VisualArtPeriodModel.class, ArtPeriodProfileInfoData.class, new RequestInterface() {

            @Override
            public BaseModel getData(ArtRestService rest, String mid) {
                return rest.getVisualArtPeriod(mid);
            }

        }),
        ART_FORM(VisualArtFormModel.class, ArtFormProfileInfoData.class, new RequestInterface() {

            @Override
            public BaseModel getData(ArtRestService rest, String mid) {
                return rest.getVisualArtForm(mid);
            }

        });

        final Class modelClass;
        final Class dataClass;
        final RequestInterface request;

        Type(Class model, Class data, RequestInterface request) {
            this.modelClass = model;
            this.dataClass = data;
            this.request = request;
        }

        public final Class getModelClass() {
            return modelClass;
        }

        public final Class getDataClass() {
            return dataClass;
        }

        public RequestInterface getRequest() {
            return request;
        }

        private static interface RequestInterface {
            public BaseModel getData(ArtRestService rest, String mid);
        }
    }

    public static void start(Context context, Type type) {
        ProfileActivity_.intent(context).type(type).start();
    }
}
