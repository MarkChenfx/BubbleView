package com.chen.bubbleview.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CHEN on 17/5/26.
 */

public class FindMusicComments implements Serializable{


    /**
     * data : {"list":[{"createTime":"2017-05-26 15:05:49","headImg":"http://yuedu.iyaoread.com/media/userPic/appUserPic/8585a4bb-7d09-46f1-a287-6dbe7d96508d/1456476803727/1456476803727.jpg","id":"5927d3cd0cf2d9900511fc5f","listenCount":13,"nickName":"灰色","second":1,"voiceUrl":"http://mov.iyaoread.com//2017052615054764750739_5926e0560cf229894acdb020.ilbc"},{"createTime":"2017-05-26 15:04:31","headImg":"http://yuedu.iyaoread.com/media/userPic/appUserPic/8585a4bb-7d09-46f1-a287-6dbe7d96508d/1456476803727/1456476803727.jpg","id":"5927d37f0cf2d9900511fc5c","listenCount":15,"nickName":"灰色","second":4,"voiceUrl":"http://mov.iyaoread.com//2017052615041600687131_5926e0560cf229894acdb020.ilbc"},{"createTime":"2017-05-26 09:06:22","headImg":"http://yuedu.iyaoread.com/media/userPic/appUserPic/9a/ac/9aac6e83-f74d-4c24-961a-8b819faf33d7/1495448767403/1495448767403.jpg","id":"59277f8e0cf2156f54381352","listenCount":14,"nickName":"小吴大侠","second":3,"voiceUrl":"http://mov.iyaoread.com//2017052609061762155788_5926e0560cf229894acdb020.ilbc"},{"createTime":"2017-05-25 21:48:05","headImg":"http://yuedu.iyaoread.com/media/userPic/appUserPic/18f76c60-de23-4c93-967e-1ed2d3e2fadf/1446705950255/1446705950255.jpg","id":"5926e0950cf229894acdb024","listenCount":19,"nickName":"蚂蚁秋歌","second":3,"voiceUrl":"http://mov.iyaoread.com//2017052521474212957937_5926e0560cf229894acdb020.ilbc"}],"totalCount":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : [{"createTime":"2017-05-26 15:05:49","headImg":"http://yuedu.iyaoread.com/media/userPic/appUserPic/8585a4bb-7d09-46f1-a287-6dbe7d96508d/1456476803727/1456476803727.jpg","id":"5927d3cd0cf2d9900511fc5f","listenCount":13,"nickName":"灰色","second":1,"voiceUrl":"http://mov.iyaoread.com//2017052615054764750739_5926e0560cf229894acdb020.ilbc"},{"createTime":"2017-05-26 15:04:31","headImg":"http://yuedu.iyaoread.com/media/userPic/appUserPic/8585a4bb-7d09-46f1-a287-6dbe7d96508d/1456476803727/1456476803727.jpg","id":"5927d37f0cf2d9900511fc5c","listenCount":15,"nickName":"灰色","second":4,"voiceUrl":"http://mov.iyaoread.com//2017052615041600687131_5926e0560cf229894acdb020.ilbc"},{"createTime":"2017-05-26 09:06:22","headImg":"http://yuedu.iyaoread.com/media/userPic/appUserPic/9a/ac/9aac6e83-f74d-4c24-961a-8b819faf33d7/1495448767403/1495448767403.jpg","id":"59277f8e0cf2156f54381352","listenCount":14,"nickName":"小吴大侠","second":3,"voiceUrl":"http://mov.iyaoread.com//2017052609061762155788_5926e0560cf229894acdb020.ilbc"},{"createTime":"2017-05-25 21:48:05","headImg":"http://yuedu.iyaoread.com/media/userPic/appUserPic/18f76c60-de23-4c93-967e-1ed2d3e2fadf/1446705950255/1446705950255.jpg","id":"5926e0950cf229894acdb024","listenCount":19,"nickName":"蚂蚁秋歌","second":3,"voiceUrl":"http://mov.iyaoread.com//2017052521474212957937_5926e0560cf229894acdb020.ilbc"}]
         * totalCount : 0
         */

        private int totalCount;
        private List<ListBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * createTime : 2017-05-26 15:05:49
             * headImg : http://yuedu.iyaoread.com/media/userPic/appUserPic/8585a4bb-7d09-46f1-a287-6dbe7d96508d/1456476803727/1456476803727.jpg
             * id : 5927d3cd0cf2d9900511fc5f
             * listenCount : 13
             * nickName : 灰色
             * second : 1
             * voiceUrl : http://mov.iyaoread.com//2017052615054764750739_5926e0560cf229894acdb020.ilbc
             */

            private int identifyStatus;

            private String fromUserId;

            public String getFromUserId() {
                return fromUserId;
            }

            public void setFromUserId(String fromUserId) {
                this.fromUserId = fromUserId;
            }

            public int getIdentifyStatus() {
                return identifyStatus;
            }

            public void setIdentifyStatus(int identifyStatus) {
                this.identifyStatus = identifyStatus;
            }

            private String createTime;
            private String headImg;
            private String id;
            private int listenCount;
            private String nickName;
            private int second;
            private String voiceUrl;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getListenCount() {
                return listenCount;
            }

            public void setListenCount(int listenCount) {
                this.listenCount = listenCount;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getSecond() {
                return second;
            }

            public void setSecond(int second) {
                this.second = second;
            }

            public String getVoiceUrl() {
                return voiceUrl;
            }

            public void setVoiceUrl(String voiceUrl) {
                this.voiceUrl = voiceUrl;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "identifyStatus=" + identifyStatus +
                        ", createTime='" + createTime + '\'' +
                        ", headImg='" + headImg + '\'' +
                        ", id='" + id + '\'' +
                        ", listenCount=" + listenCount +
                        ", nickName='" + nickName + '\'' +
                        ", second=" + second +
                        ", voiceUrl='" + voiceUrl + '\'' +
                        '}';
            }
        }
    }
}
