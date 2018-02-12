package com.abdulrahmanjavanrd.newsappchallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticlesWithRetroFit {

    RootTag mRootTag ;

    public static class RootTag {
        @SerializedName("response")
        InnerResposeTag innerRespose;

        public InnerResposeTag getInnerRespose() {
            return innerRespose;
        }
    }

    // this class for main data in json file .
    public static class InnerResposeTag {
        @SerializedName("status")
        String status;
        @SerializedName("userTier")
        String userTier;
        @SerializedName("total")
        int total;
        @SerializedName("startIndex")
        int startIndex;
        @SerializedName("pageSize")
        int pageSize;
        @SerializedName("currentPage")
        int currentPage;
        @SerializedName("pages")
        int pages;
        @SerializedName("orderBy")
        String orderBy;
        @SerializedName("results")
        List<ResultsTag> innerResultsList;

        public String getStatus() {
            return status;
        }

        public String getUserTier() {
            return userTier;
        }

        public int getTotal() {
            return total;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getPages() {
            return pages;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public List<ResultsTag> getInnerResultsList() {
            return innerResultsList;
        }
    }

    // this class for result json array, it's contain sub data .
    public static class ResultsTag {
        @SerializedName("id")
        String id;
        @SerializedName("type")
        String type;
        @SerializedName("sectionId")
        String sectionId;
        @SerializedName("sectionName")
        String sectionName;
        @SerializedName("wePublicationDate")
        String webPublicationDate;
        @SerializedName("webTitle")
        String webTitle;
        @SerializedName("webUrl")
        String webUrl;
        @SerializedName("apiUrl")
        String apiUrl;
        @SerializedName("isHosted")
        boolean ishosted;
        @SerializedName("pillarId")
        String pillarId;
        @SerializedName("pillarName")
        String pillName;
        @SerializedName("fields")
        FieldsTagToGetThumbnail fields;
        @SerializedName("tags")
        List<TagsToGetAuthor> tagsList;
        @SerializedName("blocks")
        BlocksTagToGetBodyTag blocksTag;


        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getSectionId() {
            return sectionId;
        }

        public String getSectionName() {
            return sectionName;
        }

        public String getWebPublicationDate() {
            return webPublicationDate;
        }

        public String getWebTitle() {
            return webTitle;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public boolean isIshosted() {
            return ishosted;
        }

        public String getPillarId() {
            return pillarId;
        }

        public String getPillName() {
            return pillName;
        }

        // return image for articles
        public FieldsTagToGetThumbnail getFields() {
            return fields;
        }

        // return list of json array *tags* contain firstName , and last name .
        public List<TagsToGetAuthor> getTagsList() {
            return tagsList;
        }

        public BlocksTagToGetBodyTag getBlocksTag() {
            return blocksTag;
        }
    }

    // This class for fields = json object
    public static class FieldsTagToGetThumbnail {
        @SerializedName("thumbnail")
        String thumbnail;

        public String getThumbnail() {
            return thumbnail;
        }
    }

    // This class for tags json array .
    public static class TagsToGetAuthor {
        @SerializedName("firstName")
        String firstName;
        @SerializedName("lastName")
        String lastName;

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }

    // this class for block tags , to get text summary
    public static class BlocksTagToGetBodyTag {
        @SerializedName("body")
        List<BodyTagsToGetShortText> bodyTags;


        public List<BodyTagsToGetShortText> getBodyTags() {
            return bodyTags;
        }

        @Override
        public String toString() {
            return "BlocksTagToGetBodyTag{" +
                    "bodyTags=" + bodyTags +
                    '}';
        }
    }

    public static class BodyTagsToGetShortText {
        @SerializedName("bodyTextSummary")
        String bodyTextSummary;

        public String getBodyTextSummary() {
            return bodyTextSummary;
        }
    }
}


