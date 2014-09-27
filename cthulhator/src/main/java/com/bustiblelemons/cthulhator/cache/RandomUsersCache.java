package com.bustiblelemons.cthulhator.cache;

import android.content.Context;

import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.storage.Storage;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhm on 27.09.14.
 */
public class RandomUsersCache {

    public static final String FILENAME = "random_users.json";

    private RandomUsersCache() {
    }

    private static final ObjectMapper getMapper() {
        return LazyMapperLoader.INSTANCE;
    }

    public static RandomUsersCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static int saveUsers(Context context, List<User> users) {
        return getInstance()._saveUsers(context, users);
    }

    private static void closeStream(OutputStream... streams) {
        for (OutputStream out : streams) {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void closeStream(InputStream... streams) {
        for (InputStream in : streams) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<User> getSavedUsers(Context context) {
        return getInstance()._getSavedUsers(context);
    }

    private synchronized int _saveUsers(Context context, List<User> users) {
        int r = 0;
        File file = Storage.getStorageFile(context, FILENAME);
        if (file != null) {
            RandomUserSaveModel model = new RandomUserSaveModel();
            List<User> retreived = getSavedUsers(context);
            if (retreived != null) {
                retreived.addAll(users);
            }
            r = retreived.size();
            model.setUsers(retreived);
            ObjectMapper mapper = getMapper();
            OutputStream out = null;
            try {
                out = new FileOutputStream(file);
                mapper.writeValue(out, model);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeStream(out);
            }
        }
        return r;
    }

    private synchronized List<User> _getSavedUsers(Context context) {
        List<User> users = new ArrayList<User>();
        File file = Storage.getStorageFile(context, FILENAME);
        if (file != null && file.exists() && file.canWrite()) {
            ObjectMapper mapper = getMapper();
            RandomUserSaveModel model;
            try {
                model = mapper.readValue(file, RandomUserSaveModel.class);
                if (model == null) {
                    model = new RandomUserSaveModel();
                }
                users = model.getUsers();
            } catch (IOException e) {
                e.printStackTrace();
                file.delete();
            } finally {
                return users;
            }
        }
        return users;
    }

    private static final class LazyMapperLoader {
        private static final ObjectMapper INSTANCE = new ObjectMapper();
    }

    private static final class LazyHolder {
        public static final RandomUsersCache INSTANCE = new RandomUsersCache();
    }
}
