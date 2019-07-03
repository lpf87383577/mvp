package com.shinhoandroid.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2018/4/26 14:21
 */

public class DataObj implements Serializable{

    private String id;
    private String imageId;
    private String modifiedAt;
    private SettingsBean settings;
    private String title;
    private String type;
    private List<?> columns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public SettingsBean getSettings() {
        return settings;
    }

    public void setSettings(SettingsBean settings) {
        this.settings = settings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<?> getColumns() {
        return columns;
    }

    public void setColumns(List<?> columns) {
        this.columns = columns;
    }

    public static class SettingsBean {
        /**
         * display : true
         * host : {"apple":{"lookup":{"is_can":"false"}},"cmb":{"is_used":"true"},"webview_url":"https://mtest.yimishiji.com/"}
         */

        private String display;
        private HostBean host;

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public HostBean getHost() {
            return host;
        }

        public void setHost(HostBean host) {
            this.host = host;
        }

        public static class HostBean {

            private AppleBean apple;
            private CmbBean cmb;
            private String webview_url;

            public AppleBean getApple() {
                return apple;
            }

            public void setApple(AppleBean apple) {
                this.apple = apple;
            }

            public CmbBean getCmb() {
                return cmb;
            }

            public void setCmb(CmbBean cmb) {
                this.cmb = cmb;
            }

            public String getWebview_url() {
                return webview_url;
            }

            public void setWebview_url(String webview_url) {
                this.webview_url = webview_url;
            }

            public static class AppleBean {

                private LookupBean lookup;

                public LookupBean getLookup() {
                    return lookup;
                }

                public void setLookup(LookupBean lookup) {
                    this.lookup = lookup;
                }

                public static class LookupBean {
                    /**
                     * is_can : false
                     */

                    private String is_can;

                    public String getIs_can() {
                        return is_can;
                    }

                    public void setIs_can(String is_can) {
                        this.is_can = is_can;
                    }
                }
            }

            public static class CmbBean {
                /**
                 * is_used : true
                 */

                private String is_used;

                public String getIs_used() {
                    return is_used;
                }

                public void setIs_used(String is_used) {
                    this.is_used = is_used;
                }
            }
        }
    }


}
