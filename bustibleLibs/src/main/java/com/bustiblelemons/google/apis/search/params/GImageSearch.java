package com.bustiblelemons.google.apis.search.params;

import com.bustiblelemons.api.OnlineQuery;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

/**
 * Created by bhm on 18.07.14.
 */
public interface GImageSearch extends OnlineQuery {
    String VERSION   = "v";
    String VERSION_1 = "1.0";
    String URL       = "https://ajax.googleapis.com/ajax/services/search/images";
    String SCHEME    = "http";
    String HOST      = "ajax.googleapis.com";
    String METHOD    = "ajax/services/search/images";
    String QUERY     = "q";
    String START     = "start";

    public enum as_rights {
        cc_publicdomain {
            @Override
            public String getDescription() {
                return "restricts search results to images with the publicdomain label";
            }
        }, cc_attribute {
            @Override
            public String getDescription() {
                return "restricts search results to images with the attribute label.";
            }
        }, cc_sharealike {
            @Override
            public String getDescription() {
                return "restricts search results to images with the sharealike label";
            }
        }, cc_noncommercial {
            @Override
            public String getDescription() {
                return "restricts search results to images with the noncomercial label.";
            }
        }, cc_nonderived {
            @Override
            public String getDescription() {
                return "restricts search results to images with the nonderived label.";
            }
        }, none {
            @Override
            public String getDescription() {
                return "all bets are off";
            }
        };

        public abstract String getDescription();
    }

    enum as_filetype {
        jpg, png, gif, bmp;
    }

    enum imgsz {
        icon, small, medium, large, xlarge, xxlarge, huge;
    }

    enum imgtype {
        face, photo, clipart, lineart;
    }

    public enum safe {
        active, moderate, off;
    }

    safe getSafeMode();

    Enum<?> getImageType();

    as_filetype getFileType();

    imgsz getImageSize();

    as_rights getRights();

    enum imgc {
        gray, color;
    }

    String hl               = Locale.getDefault().getLanguage();
    String RESULTS_PER_PAGE = "rsz";
    int    rsz              = 4;

    HttpResponse query(String query) throws IOException, URISyntaxException;
}
