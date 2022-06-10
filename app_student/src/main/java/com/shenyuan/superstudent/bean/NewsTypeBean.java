package com.shenyuan.superstudent.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.NewsBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/12 14:59
 * desc
 */
public class NewsTypeBean implements Serializable {
    /**
     * id : -1
     * newsTypeName : 推荐
     * sort : null
     * newsListVoList : {"records":[{"id":11,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035333Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=7d86f9651b2e6e95b90e6256e1808d49aa0cbcd5f3c14e1b03c7cb85596ee735","signature":"wqeq","content":"阿斯蒂芬就卡了放假啊","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:34","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":10,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035328Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=c30b4d2f9adbeebece81483c08294f2598eb468e65cf9c514d28f09a45b392f2","signature":"wqeq","content":"撒旦法姐啊看风景","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:29","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":9,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035323Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=6b99ba4e11abab1b73d2d676d1b00a183ef22bae3e7bfa4c57c7751665fb1497","signature":"wqeq","content":"sdfasdfa","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:24","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":8,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035316Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=f2c9080a2d2082c35b77934be6d8a358e08a1b36ef449628fa3c416e61f11391","signature":"wqeq","content":"sdfsfs","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:17","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":7,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035311Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=a775b8bc91d7916b0b5f56aa8e248dde4523ace2b8129de8ace55ddfba40a07d","signature":"wqeq","content":"岁的flak了","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:11","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":6,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035306Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=6abaf93672e8c63a8d61a9f07b2936731353a7f130ee385603b6557e7944ee66","signature":"wqeq","content":"sdfs","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:07","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":5,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035257Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=30a5c5d3f63093143745240ad50b4a0a12ce24692b9b682b85604325f0909ddc","signature":"wqeq","content":"我认为","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:52:57","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":4,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035243Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=58a0ead191eb128ac06d9034defc2f1690f0c15e1470628c25de200bc438daf0","signature":"wqeq","content":"13123","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:52:43","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null}],"total":8,"size":10,"current":1,"orders":[],"optimizeCountSql":true,"hitCount":false,"countId":null,"maxLimit":null,"searchCount":true,"pages":1}
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "newsTypeName")
    private String newsTypeName;
    @JSONField(name = "sort")
    private String sort;
    @JSONField(name = "newsListVoList")
    private NewsListVoListDTO newsListVoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public NewsListVoListDTO getNewsListVoList() {
        return newsListVoList;
    }

    public void setNewsListVoList(NewsListVoListDTO newsListVoList) {
        this.newsListVoList = newsListVoList;
    }

    public static class NewsListVoListDTO implements Serializable {
        /**
         * records : [{"id":11,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035333Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=7d86f9651b2e6e95b90e6256e1808d49aa0cbcd5f3c14e1b03c7cb85596ee735","signature":"wqeq","content":"阿斯蒂芬就卡了放假啊","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:34","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":10,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035328Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=c30b4d2f9adbeebece81483c08294f2598eb468e65cf9c514d28f09a45b392f2","signature":"wqeq","content":"撒旦法姐啊看风景","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:29","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":9,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035323Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=6b99ba4e11abab1b73d2d676d1b00a183ef22bae3e7bfa4c57c7751665fb1497","signature":"wqeq","content":"sdfasdfa","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:24","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":8,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035316Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=f2c9080a2d2082c35b77934be6d8a358e08a1b36ef449628fa3c416e61f11391","signature":"wqeq","content":"sdfsfs","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:17","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":7,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035311Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=a775b8bc91d7916b0b5f56aa8e248dde4523ace2b8129de8ace55ddfba40a07d","signature":"wqeq","content":"岁的flak了","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:11","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":6,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035306Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=6abaf93672e8c63a8d61a9f07b2936731353a7f130ee385603b6557e7944ee66","signature":"wqeq","content":"sdfs","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:53:07","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":5,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035257Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=30a5c5d3f63093143745240ad50b4a0a12ce24692b9b682b85604325f0909ddc","signature":"wqeq","content":"我认为","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:52:57","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null},{"id":4,"newsTitle":"weq","newsTypeId":1,"isSubscribed":1,"isSubscribedInfo":null,"pictureUrl":"http://192.168.30.139:9000/jysyfile/%E7%99%BE%E4%B8%87%E6%89%A9%E6%8B%9B%E4%BB%A3%E7%A0%81%E5%AD%97%E5%85%B8%E8%A1%A8.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T035243Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=58a0ead191eb128ac06d9034defc2f1690f0c15e1470628c25de200bc438daf0","signature":"wqeq","content":"13123","status":1,"statusInfo":null,"publishTime":null,"publisherId":null,"publisherName":null,"deleted":false,"viewCount":0,"creatorId":null,"creatorName":null,"createTime":"2021-03-03 11:52:43","updaterId":null,"updaterName":null,"updateTime":null,"newsTypeName":"再次测试","endpointInfo":null,"endpoint":1,"userId":null}]
         * total : 8
         * size : 10
         * current : 1
         * orders : []
         * optimizeCountSql : true
         * hitCount : false
         * countId : null
         * maxLimit : null
         * searchCount : true
         * pages : 1
         */

        @JSONField(name = "records")
        private List<NewsBean> records;
        @JSONField(name = "total")
        private Integer total;
        @JSONField(name = "size")
        private Integer size;
        @JSONField(name = "current")
        private Integer current;
        @JSONField(name = "optimizeCountSql")
        private boolean optimizeCountSql;
        @JSONField(name = "hitCount")
        private boolean hitCount;
        @JSONField(name = "countId")
        private String countId;
        @JSONField(name = "maxLimit")
        private String maxLimit;
        @JSONField(name = "searchCount")
        private boolean searchCount;
        @JSONField(name = "pages")
        private Integer pages;

        public List<NewsBean> getRecords() {
            return records;
        }

        public void setRecords(List<NewsBean> records) {
            this.records = records;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Integer getCurrent() {
            return current;
        }

        public void setCurrent(Integer current) {
            this.current = current;
        }

        public boolean getOptimizeCountSql() {
            return optimizeCountSql;
        }

        public void setOptimizeCountSql(boolean optimizeCountSql) {
            this.optimizeCountSql = optimizeCountSql;
        }

        public boolean getHitCount() {
            return hitCount;
        }

        public void setHitCount(boolean hitCount) {
            this.hitCount = hitCount;
        }

        public String getCountId() {
            return countId;
        }

        public void setCountId(String countId) {
            this.countId = countId;
        }

        public String getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(String maxLimit) {
            this.maxLimit = maxLimit;
        }

        public boolean getSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }
    }
}
