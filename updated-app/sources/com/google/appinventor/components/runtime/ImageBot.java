package com.google.appinventor.components.runtime;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import com.google.appinventor.common.version.AppInventorFeatures;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.imagebot.ImageBotToken;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.Base58Util;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.IOUtils;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.protobuf.ByteString;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

@DesignerComponent(androidMinSdk = 9, category = ComponentCategory.EXPERIMENTAL, iconName = "images/paintpalette.png", nonVisible = true, version = 2)
@UsesLibraries(libraries = "protobuf-java-3.0.0.jar")
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public class ImageBot extends AndroidNonvisibleComponent {
    private static final String COMODO_ROOT = "-----BEGIN CERTIFICATE-----\nMIIENjCCAx6gAwIBAgIBATANBgkqhkiG9w0BAQUFADBvMQswCQYDVQQGEwJTRTEU\nMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFkZFRydXN0IEV4dGVybmFs\nIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBFeHRlcm5hbCBDQSBSb290\nMB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFowbzELMAkGA1UEBhMCU0Ux\nFDASBgNVBAoTC0FkZFRydXN0IEFCMSYwJAYDVQQLEx1BZGRUcnVzdCBFeHRlcm5h\nbCBUVFAgTmV0d29yazEiMCAGA1UEAxMZQWRkVHJ1c3QgRXh0ZXJuYWwgQ0EgUm9v\ndDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALf3GjPm8gAELTngTlvt\nH7xsD821+iO2zt6bETOXpClMfZOfvUq8k+0DGuOPz+VtUFrWlymUWoCwSXrbLpX9\nuMq/NzgtHj6RQa1wVsfwTz/oMp50ysiQVOnGXw94nZpAPA6sYapeFI+eh6FqUNzX\nmk6vBbOmcZSccbNQYArHE504B4YCqOmoaSYYkKtMsE8jqzpPhNjfzp/haW+710LX\na0Tkx63ubUFfclpxCDezeWWkWaCUN/cALw3CknLa0Dhy2xSoRcRdKn23tNbE7qzN\nE0S3ySvdQwAl+mG5aWpYIxG3pzOPVnVZ9c0p10a3CitlttNCbxWyuHv77+ldU9U0\nWicCAwEAAaOB3DCB2TAdBgNVHQ4EFgQUrb2YejS0Jvf6xCZU7wO94CTLVBowCwYD\nVR0PBAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wgZkGA1UdIwSBkTCBjoAUrb2YejS0\nJvf6xCZU7wO94CTLVBqhc6RxMG8xCzAJBgNVBAYTAlNFMRQwEgYDVQQKEwtBZGRU\ncnVzdCBBQjEmMCQGA1UECxMdQWRkVHJ1c3QgRXh0ZXJuYWwgVFRQIE5ldHdvcmsx\nIjAgBgNVBAMTGUFkZFRydXN0IEV4dGVybmFsIENBIFJvb3SCAQEwDQYJKoZIhvcN\nAQEFBQADggEBALCb4IUlwtYj4g+WBpKdQZic2YR5gdkeWxQHIzZlj7DYd7usQWxH\nYINRsPkyPef89iYTx4AWpb9a/IfPeHmJIZriTAcKhjW88t5RxNKWt9x+Tu5w/Rw5\n6wwCURQtjr0W4MHfRnXnJK3s9EK0hZNwEGe6nQY1ShjTK3rMUUKhemPR5ruhxSvC\nNr4TDea9Y355e6cJDUCrat2PisP29owaQgVR1EX1n6diIWgVIEM8med8vSTYqZEX\nc4g/VhsxOBi0cQ+azcgOno4uG+GMmIPLHzHxREzGBHNJdmAPx/i9F4BrLunMTA5a\nmnkPIAou1Z5jJh5VkpTYghdae9C8x49OhgQ=\n-----END CERTIFICATE-----\n";
    private static final String COMODO_USRTRUST = "-----BEGIN CERTIFICATE-----\nMIIFdzCCBF+gAwIBAgIQE+oocFv07O0MNmMJgGFDNjANBgkqhkiG9w0BAQwFADBv\nMQswCQYDVQQGEwJTRTEUMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFk\nZFRydXN0IEV4dGVybmFsIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBF\neHRlcm5hbCBDQSBSb290MB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFow\ngYgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpOZXcgSmVyc2V5MRQwEgYDVQQHEwtK\nZXJzZXkgQ2l0eTEeMBwGA1UEChMVVGhlIFVTRVJUUlVTVCBOZXR3b3JrMS4wLAYD\nVQQDEyVVU0VSVHJ1c3QgUlNBIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MIICIjAN\nBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgBJlFzYOw9sIs9CsVw127c0n00yt\nUINh4qogTQktZAnczomfzD2p7PbPwdzx07HWezcoEStH2jnGvDoZtF+mvX2do2NC\ntnbyqTsrkfjib9DsFiCQCT7i6HTJGLSR1GJk23+jBvGIGGqQIjy8/hPwhxR79uQf\njtTkUcYRZ0YIUcuGFFQ/vDP+fmyc/xadGL1RjjWmp2bIcmfbIWax1Jt4A8BQOujM\n8Ny8nkz+rwWWNR9XWrf/zvk9tyy29lTdyOcSOk2uTIq3XJq0tyA9yn8iNK5+O2hm\nAUTnAU5GU5szYPeUvlM3kHND8zLDU+/bqv50TmnHa4xgk97Exwzf4TKuzJM7UXiV\nZ4vuPVb+DNBpDxsP8yUmazNt925H+nND5X4OpWaxKXwyhGNVicQNwZNUMBkTrNN9\nN6frXTpsNVzbQdcS2qlJC9/YgIoJk2KOtWbPJYjNhLixP6Q5D9kCnusSTJV882sF\nqV4Wg8y4Z+LoE53MW4LTTLPtW//e5XOsIzstAL81VXQJSdhJWBp/kjbmUZIO8yZ9\nHE0XvMnsQybQv0FfQKlERPSZ51eHnlAfV1SoPv10Yy+xUGUJ5lhCLkMaTLTwJUdZ\n+gQek9QmRkpQgbLevni3/GcV4clXhB4PY9bpYrrWX1Uu6lzGKAgEJTm4Diup8kyX\nHAc/DVL17e8vgg8CAwEAAaOB9DCB8TAfBgNVHSMEGDAWgBStvZh6NLQm9/rEJlTv\nA73gJMtUGjAdBgNVHQ4EFgQUU3m/WqorSs9UgOHYm8Cd8rIDZsswDgYDVR0PAQH/\nBAQDAgGGMA8GA1UdEwEB/wQFMAMBAf8wEQYDVR0gBAowCDAGBgRVHSAAMEQGA1Ud\nHwQ9MDswOaA3oDWGM2h0dHA6Ly9jcmwudXNlcnRydXN0LmNvbS9BZGRUcnVzdEV4\ndGVybmFsQ0FSb290LmNybDA1BggrBgEFBQcBAQQpMCcwJQYIKwYBBQUHMAGGGWh0\ndHA6Ly9vY3NwLnVzZXJ0cnVzdC5jb20wDQYJKoZIhvcNAQEMBQADggEBAJNl9jeD\nlQ9ew4IcH9Z35zyKwKoJ8OkLJvHgwmp1ocd5yblSYMgpEg7wrQPWCcR23+WmgZWn\nRtqCV6mVksW2jwMibDN3wXsyF24HzloUQToFJBv2FAY7qCUkDrvMKnXduXBBP3zQ\nYzYhBx9G/2CkkeFnvN4ffhkUyWNnkepnB2u0j4vAbkN9w6GAbLIevFOFfdyQoaS8\nLe9Gclc1Bb+7RrtubTeZtv8jkpHGbkD4jylW6l/VXxRTrPBPYer3IsynVgviuDQf\nJtl7GQVoP7o81DgGotPmjw7jtHFtQELFhLRAlSv0ZaBIefYdgWOWnU914Ph85I6p\n0fKtirOMxyHNwu8=\n-----END CERTIFICATE-----\n";
    private static final boolean DEBUG = false;
    private static final String IMAGEBOT_SERVICE_URL = (AppInventorFeatures.chatBotHost() + "image/v1");
    private static final String ISRG_ROOT_X1 = "-----BEGIN CERTIFICATE-----\nMIIFazCCA1OgAwIBAgIRAIIQz7DSQONZRGPgu2OCiwAwDQYJKoZIhvcNAQELBQAw\nTzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh\ncmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMTUwNjA0MTEwNDM4\nWhcNMzUwNjA0MTEwNDM4WjBPMQswCQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJu\nZXQgU2VjdXJpdHkgUmVzZWFyY2ggR3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBY\nMTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAK3oJHP0FDfzm54rVygc\nh77ct984kIxuPOZXoHj3dcKi/vVqbvYATyjb3miGbESTtrFj/RQSa78f0uoxmyF+\n0TM8ukj13Xnfs7j/EvEhmkvBioZxaUpmZmyPfjxwv60pIgbz5MDmgK7iS4+3mX6U\nA5/TR5d8mUgjU+g4rk8Kb4Mu0UlXjIB0ttov0DiNewNwIRt18jA8+o+u3dpjq+sW\nT8KOEUt+zwvo/7V3LvSye0rgTBIlDHCNAymg4VMk7BPZ7hm/ELNKjD+Jo2FR3qyH\nB5T0Y3HsLuJvW5iB4YlcNHlsdu87kGJ55tukmi8mxdAQ4Q7e2RCOFvu396j3x+UC\nB5iPNgiV5+I3lg02dZ77DnKxHZu8A/lJBdiB3QW0KtZB6awBdpUKD9jf1b0SHzUv\nKBds0pjBqAlkd25HN7rOrFleaJ1/ctaJxQZBKT5ZPt0m9STJEadao0xAH0ahmbWn\nOlFuhjuefXKnEgV4We0+UXgVCwOPjdAvBbI+e0ocS3MFEvzG6uBQE3xDk3SzynTn\njh8BCNAw1FtxNrQHusEwMFxIt4I7mKZ9YIqioymCzLq9gwQbooMDQaHWBfEbwrbw\nqHyGO0aoSCqI3Haadr8faqU9GY/rOPNk3sgrDQoo//fb4hVC1CLQJ13hef4Y53CI\nrU7m2Ys6xt0nUW7/vGT1M0NPAgMBAAGjQjBAMA4GA1UdDwEB/wQEAwIBBjAPBgNV\nHRMBAf8EBTADAQH/MB0GA1UdDgQWBBR5tFnme7bl5AFzgAiIyBpY9umbbjANBgkq\nhkiG9w0BAQsFAAOCAgEAVR9YqbyyqFDQDLHYGmkgJykIrGF1XIpu+ILlaS/V9lZL\nubhzEFnTIZd+50xx+7LSYK05qAvqFyFWhfFQDlnrzuBZ6brJFe+GnY+EgPbk6ZGQ\n3BebYhtF8GaV0nxvwuo77x/Py9auJ/GpsMiu/X1+mvoiBOv/2X/qkSsisRcOj/KK\nNFtY2PwByVS5uCbMiogziUwthDyC3+6WVwW6LLv3xLfHTjuCvjHIInNzktHCgKQ5\nORAzI4JMPJ+GslWYHb4phowim57iaztXOoJwTdwJx4nLCgdNbOhdjsnvzqvHu7Ur\nTkXWStAmzOVyyghqpZXjFaH3pO3JLF+l+/+sKAIuvtd7u+Nxe5AW0wdeRlN8NwdC\njNPElpzVmbUq4JUagEiuTDkHzsxHpFKVK7q4+63SM1N95R1NbdWhscdCb+ZAJzVc\noyi3B43njTOQ5yOf+1CceWxG1bQVs5ZufpsMljq4Ui0/1lvh+wjChP4kqKOJ2qxq\n4RgqsahDYVvTH9w7jXbyLeiNdd8XM2w9U/t7y0Ff/9yi0GE44Za4rF2LN9d11TPA\nmRGunUHBcnWEvgJBQl9nJEiU0Zsnvgc/ubhPgXRR4Xq37Z0j4r7g1SgEEzwxA57d\nemyPxgcYxn/eR44/KJ4EBs+lVDR3veyJm+kXQ99b21/+jh5Xos1AnX5iItreGCc=\n-----END CERTIFICATE-----\n";
    public static final String LOG_TAG = ImageBot.class.getSimpleName();
    private static final String MIT_CA = "-----BEGIN CERTIFICATE-----\nMIIFXjCCBEagAwIBAgIJAMLfrRWIaHLbMA0GCSqGSIb3DQEBCwUAMIHPMQswCQYD\nVQQGEwJVUzELMAkGA1UECBMCTUExEjAQBgNVBAcTCUNhbWJyaWRnZTEuMCwGA1UE\nChMlTWFzc2FjaHVzZXR0cyBJbnN0aXR1dGUgb2YgVGVjaG5vbG9neTEZMBcGA1UE\nCxMQTUlUIEFwcCBJbnZlbnRvcjEmMCQGA1UEAxMdQ2xvdWREQiBDZXJ0aWZpY2F0\nZSBBdXRob3JpdHkxEDAOBgNVBCkTB0Vhc3lSU0ExGjAYBgkqhkiG9w0BCQEWC2pp\nc0BtaXQuZWR1MB4XDTE3MTIyMjIyMzkyOVoXDTI3MTIyMDIyMzkyOVowgc8xCzAJ\nBgNVBAYTAlVTMQswCQYDVQQIEwJNQTESMBAGA1UEBxMJQ2FtYnJpZGdlMS4wLAYD\nVQQKEyVNYXNzYWNodXNldHRzIEluc3RpdHV0ZSBvZiBUZWNobm9sb2d5MRkwFwYD\nVQQLExBNSVQgQXBwIEludmVudG9yMSYwJAYDVQQDEx1DbG91ZERCIENlcnRpZmlj\nYXRlIEF1dGhvcml0eTEQMA4GA1UEKRMHRWFzeVJTQTEaMBgGCSqGSIb3DQEJARYL\namlzQG1pdC5lZHUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDHzI3D\nFobNDv2HTWlDdedmbxZIJYSqWlzdRJC3oVJgCubdAs46WJRqUxDRWft9UpYGMKkw\nmYN8mdPby2m5OJagdVIZgnguB71zIQkC8yMzd94FC3gldX5m7R014D/0fkpzvsSt\n6fsNectJT0k7gPELOH6t4u6AUbvIsEX0nNyRWsmA/ucXCsDBwXyBJxfOKIQ9tDI4\n/WfcKk9JDpeMF7RP0CIOtlAPotKIaPoY1W3eMIi/0riOt5vTFsB8pxhxAVy0cfGX\niHukdrAkAJixTgkyS7wzk22xOeXVnRIzAMGK5xHMDw/HRQGTrUGfIXHENV3u+3Ae\nL5/ZoQwyZTixmQNzAgMBAAGjggE5MIIBNTAdBgNVHQ4EFgQUZfMKQXqtC5UJGFrZ\ngZE1nmlx+t8wggEEBgNVHSMEgfwwgfmAFGXzCkF6rQuVCRha2YGRNZ5pcfrfoYHV\npIHSMIHPMQswCQYDVQQGEwJVUzELMAkGA1UECBMCTUExEjAQBgNVBAcTCUNhbWJy\naWRnZTEuMCwGA1UEChMlTWFzc2FjaHVzZXR0cyBJbnN0aXR1dGUgb2YgVGVjaG5v\nbG9neTEZMBcGA1UECxMQTUlUIEFwcCBJbnZlbnRvcjEmMCQGA1UEAxMdQ2xvdWRE\nQiBDZXJ0aWZpY2F0ZSBBdXRob3JpdHkxEDAOBgNVBCkTB0Vhc3lSU0ExGjAYBgkq\nhkiG9w0BCQEWC2ppc0BtaXQuZWR1ggkAwt+tFYhoctswDAYDVR0TBAUwAwEB/zAN\nBgkqhkiG9w0BAQsFAAOCAQEAIkKr3eIvwZO6a1Jsh3qXwveVnrqwxYvLw2IhTwNT\n/P6C5jbRnzUuDuzg5sEIpbBo/Bp3qIp7G5cdVOkIrqO7uCp6Kyc7d9lPsEe/cbF4\naNwNmdWroRN1y0tuMU6+z7frd5pOeAZP9E/DM/0Uaz4yVzwnlvZUttaLymyMhH54\nisGQKbAqHDFtKZvb6DxsHzrO2YgeaBAtjeVhPWiv8BhzbOo9+hhZvYHYtoM2W+Ze\nDHuvv0v+qouphftDKVBp16N8Pk5WgabTXzV6VcNee92iwbWYDEv06+S3AF/q2TBe\nxxXtAa5ywbp6IRF37QuQChcYnOx7zIylYI1PIENfQFC2BA==\n-----END CERTIFICATE-----\n";
    private String apiKey = "";
    private boolean invert = true;
    private int size = 256;
    private SSLSocketFactory sslSockFactory;
    private String token;

    public ImageBot(ComponentContainer container) {
        super(container.$form());
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty(category = PropertyCategory.ADVANCED, description = "The MIT Access token to use. MIT App Inventor will automatically fill this value in. You should not need to change it.", userVisible = true)
    public void Token(String token2) {
        this.token = token2;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void ApiKey(String apiKey2) {
        this.apiKey = apiKey2;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void InvertMask(boolean invert2) {
        this.invert = invert2;
    }

    @SimpleProperty
    public boolean InvertMask() {
        return this.invert;
    }

    @DesignerProperty(defaultValue = "256", editorType = "non_negative_integer")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void Size(int size2) {
        this.size = size2;
    }

    @SimpleProperty
    public int Size() {
        return this.size;
    }

    @SimpleFunction
    public void CreateImage(final String description) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                ImageBot.this.doCreateImage(description);
            }
        });
    }

    @Deprecated
    @SimpleFunction
    public void EditImage(Object source, final String description) {
        try {
            final Bitmap bitmap = loadImage(source);
            if (bitmap != null) {
                AsynchUtil.runAsynchronously(new Runnable() {
                    public void run() {
                        ImageBot.this.doEditImage(bitmap, (Bitmap) null, description);
                    }
                });
            } else {
                this.form.androidUIHandler.postDelayed(new Runnable() {
                    public void run() {
                        ImageBot.this.ErrorOccurred(-1, "Invalid input to EditImage");
                    }
                }, 0);
                throw new StopBlocksExecution();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to read source image", e);
        }
    }

    @SimpleFunction
    public void EditImageWithMask(Object imageSource, Object maskSource, final String prompt) {
        try {
            final Bitmap bitmap = loadImage(imageSource);
            final Bitmap mask = loadMask(maskSource);
            if (bitmap != null && mask != null) {
                AsynchUtil.runAsynchronously(new Runnable() {
                    public void run() {
                        ImageBot.this.doEditImage(bitmap, mask, prompt);
                    }
                });
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to read source image", e);
        }
    }

    @SimpleEvent
    public void ImageCreated(String fileName) {
        EventDispatcher.dispatchEvent(this, "ImageCreated", fileName);
    }

    @SimpleEvent
    public void ImageEdited(String fileName) {
        EventDispatcher.dispatchEvent(this, "ImageEdited", fileName);
    }

    @SimpleEvent
    public void ErrorOccurred(int responseCode, String responseText) {
        if (!EventDispatcher.dispatchEvent(this, "ErrorOccurred", Integer.valueOf(responseCode), responseText)) {
            this.form.dispatchErrorOccurredEvent(this, "ErrorOccurred", ErrorMessages.ERROR_IMAGEBOT_ERROR, Integer.valueOf(responseCode), responseText);
        }
    }

    private static class ImageException extends Exception {
        private final int code;
        private final String description;

        private ImageException(int code2, String description2, Throwable cause) {
            super(cause);
            this.code = code2;
            this.description = description2;
        }

        /* access modifiers changed from: private */
        public String getResponseMessage() {
            return this.description;
        }

        /* access modifiers changed from: private */
        public int getResponseCode() {
            return this.code;
        }
    }

    /* access modifiers changed from: private */
    public void doCreateImage(String prompt) {
        String iToken;
        try {
            if (this.token == null || this.token.equals("") || this.token.charAt(0) != '%') {
                iToken = this.token;
            } else {
                iToken = this.token.substring(1);
            }
            ImageBotToken.request.Builder builder = ImageBotToken.request.newBuilder().setToken(ImageBotToken.token.parseFrom(Base58Util.decode(iToken))).setSize(new StringBuilder().append(this.size).toString()).setOperation(ImageBotToken.request.OperationType.CREATE).setPrompt(prompt);
            if (this.apiKey != null && !this.apiKey.isEmpty()) {
                builder = builder.setApikey(this.apiKey);
            }
            try {
                final String response = sendRequest(builder.build());
                this.form.runOnUiThread(new Runnable() {
                    public void run() {
                        ImageBot.this.ImageCreated(response);
                    }
                });
            } catch (ImageException e) {
                Log.e(LOG_TAG, "Unable to create image", e);
                this.form.runOnUiThread(new Runnable() {
                    public void run() {
                        ImageBot.this.ErrorOccurred(e.getResponseCode(), e.getResponseMessage());
                    }
                });
            }
        } catch (Exception e2) {
            Log.e(LOG_TAG, "Unable to create image", e2);
            this.form.runOnUiThread(new Runnable() {
                public void run() {
                    ImageBot.this.ErrorOccurred(404, e2.toString());
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void doEditImage(Bitmap source, Bitmap mask, String description) {
        String iToken;
        ByteArrayOutputStream sourceBuffer = new ByteArrayOutputStream();
        source.compress(Bitmap.CompressFormat.PNG, 100, sourceBuffer);
        ByteString sourceString = ByteString.copyFrom(sourceBuffer.toByteArray());
        ByteString maskString = null;
        if (mask != null) {
            ByteArrayOutputStream maskBuffer = new ByteArrayOutputStream();
            mask.compress(Bitmap.CompressFormat.PNG, 100, maskBuffer);
            maskString = ByteString.copyFrom(maskBuffer.toByteArray());
        }
        if (this.token == null || this.token.equals("") || this.token.charAt(0) != '%') {
            iToken = this.token;
        } else {
            iToken = this.token.substring(1);
        }
        try {
            ImageBotToken.request.Builder builder = ImageBotToken.request.newBuilder().setToken(ImageBotToken.token.parseFrom(Base58Util.decode(iToken))).setSource(sourceString).setOperation(ImageBotToken.request.OperationType.EDIT).setSize(new StringBuilder().append(this.size).toString()).setPrompt(description);
            if (this.apiKey != null && !this.apiKey.isEmpty()) {
                builder = builder.setApikey(this.apiKey);
            }
            if (maskString != null) {
                builder.setMask(maskString);
            }
            try {
                final String response = sendRequest(builder.build());
                this.form.runOnUiThread(new Runnable() {
                    public void run() {
                        ImageBot.this.ImageEdited(response);
                    }
                });
            } catch (ImageException e) {
                Log.e(LOG_TAG, "Unable to edit image", e);
                this.form.runOnUiThread(new Runnable() {
                    public void run() {
                        ImageBot.this.ErrorOccurred(e.getResponseCode(), e.getResponseMessage());
                    }
                });
            }
        } catch (IOException e2) {
            this.form.runOnUiThread(new Runnable() {
                public void run() {
                    ImageBot.this.ErrorOccurred(ErrorMessages.ERROR_NXT_INVALID_RETURN_PACKAGE, "Invalid Token");
                }
            });
        }
    }

    private String sendRequest(ImageBotToken.request request) throws ImageException {
        FileOutputStream out;
        HttpsURLConnection connection = null;
        ensureSslSockFactory();
        int responseCode = -1;
        try {
            connection = (HttpsURLConnection) new URL(IMAGEBOT_SERVICE_URL).openConnection();
            if (connection != null) {
                connection.setSSLSocketFactory(this.sslSockFactory);
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                request.writeTo(connection.getOutputStream());
                responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    byte[] imageData = ImageBotToken.response.parseFrom(connection.getInputStream()).getImage().toByteArray();
                    File outFile = FileUtil.getOutputFile("ImageBot");
                    out = new FileOutputStream(outFile);
                    out.write(imageData);
                    out.flush();
                    out.close();
                    String uri = Uri.fromFile(outFile).toString();
                    if (connection != null) {
                        connection.disconnect();
                    }
                    return uri;
                }
                throw new ImageException(responseCode, IOUtils.readStreamAsString(connection.getErrorStream()), (Throwable) null);
            }
            if (connection != null) {
                connection.disconnect();
            }
            throw new ImageException(404, "Could not connect to proxy server", (Throwable) null);
        } catch (IOException e) {
            try {
                Log.e(LOG_TAG, "Got an IOException", e);
                throw new ImageException(responseCode, e.toString(), e);
            } catch (Throwable th) {
                if (connection != null) {
                    connection.disconnect();
                }
                throw th;
            }
        } catch (Throwable th2) {
            out.close();
            throw th2;
        }
    }

    private Bitmap loadImage(Object source) throws IOException {
        Bitmap bitmap;
        Log.d(LOG_TAG, "loadImage source = " + source);
        if (source instanceof Canvas) {
            bitmap = ((Canvas) source).getBitmap();
        } else if (source instanceof Image) {
            bitmap = ((BitmapDrawable) ((Image) source).getView().getBackground()).getBitmap();
        } else {
            bitmap = MediaUtil.getBitmapDrawable(this.form, source.toString()).getBitmap();
        }
        if (bitmap == null) {
            return bitmap;
        }
        if (bitmap.getWidth() == this.size && bitmap.getHeight() == this.size) {
            return bitmap;
        }
        return Bitmap.createScaledBitmap(bitmap, this.size, this.size, false);
    }

    private Bitmap loadMask(Object mask) throws IOException {
        Bitmap bitmap = loadImage(mask);
        if (!this.invert) {
            return bitmap;
        }
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(new ColorMatrix(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 255.0f}));
        Paint paint = new Paint();
        paint.setColorFilter(filter);
        Bitmap newBitmap = Bitmap.createBitmap(this.size, this.size, Bitmap.Config.ARGB_8888);
        new Canvas(newBitmap).drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return newBitmap;
    }

    private synchronized void ensureSslSockFactory() {
        if (this.sslSockFactory == null) {
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream caInput = new ByteArrayInputStream(COMODO_ROOT.getBytes(StandardCharsets.UTF_8));
                Certificate ca = cf.generateCertificate(caInput);
                caInput.close();
                ByteArrayInputStream caInput2 = new ByteArrayInputStream(COMODO_USRTRUST.getBytes(StandardCharsets.UTF_8));
                Certificate inter = cf.generateCertificate(caInput2);
                caInput2.close();
                ByteArrayInputStream caInput3 = new ByteArrayInputStream(MIT_CA.getBytes(StandardCharsets.UTF_8));
                Certificate mitca = cf.generateCertificate(caInput3);
                caInput3.close();
                ByteArrayInputStream caInput4 = new ByteArrayInputStream(ISRG_ROOT_X1.getBytes(StandardCharsets.UTF_8));
                Certificate isrg = cf.generateCertificate(caInput4);
                caInput4.close();
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load((InputStream) null, (char[]) null);
                int count = 1;
                X509Certificate[] systemCertificates = getSystemCertificates();
                int length = systemCertificates.length;
                for (int i = 0; i < length; i++) {
                    keyStore.setCertificateEntry("root" + count, systemCertificates[i]);
                    count++;
                }
                keyStore.setCertificateEntry("comodo", ca);
                keyStore.setCertificateEntry("inter", inter);
                keyStore.setCertificateEntry("mitca", mitca);
                keyStore.setCertificateEntry("isrg", isrg);
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(keyStore);
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init((KeyManager[]) null, tmf.getTrustManagers(), (SecureRandom) null);
                this.sslSockFactory = ctx.getSocketFactory();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Could not setup SSL Trust Store for ImageBot", e);
                throw new YailRuntimeError("Could Not setup SSL Trust Store for ImageBot: ", e.getMessage());
            }
        }
    }

    private X509Certificate[] getSystemCertificates() {
        try {
            TrustManagerFactory otmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = null;
            otmf.init((KeyStore) null);
            return ((X509TrustManager) otmf.getTrustManagers()[0]).getAcceptedIssuers();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Getting System Certificates", e);
            return new X509Certificate[0];
        }
    }
}
