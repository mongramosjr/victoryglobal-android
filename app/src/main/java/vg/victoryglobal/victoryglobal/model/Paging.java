/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 11/27/17 6:34 AM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 11/27/17 6:34 AM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mong on 11/27/17.
 */

public class Paging implements Parcelable{

    public Integer page;
    public Integer current;
    public Integer count;
    public Integer perPage;
    public Boolean prevPage;
    public Boolean nextPage;
    public Integer pageCount;
    public String sort;
    public String direction;
    public Integer limit;

    protected Paging(Parcel in) {
        if (in.readByte() == 0) {
            page = null;
        } else {
            page = in.readInt();
        }
        if (in.readByte() == 0) {
            current = null;
        } else {
            current = in.readInt();
        }
        if (in.readByte() == 0) {
            count = null;
        } else {
            count = in.readInt();
        }
        if (in.readByte() == 0) {
            perPage = null;
        } else {
            perPage = in.readInt();
        }
        byte tmpPrevPage = in.readByte();
        prevPage = tmpPrevPage == 0 ? null : tmpPrevPage == 1;
        byte tmpNextPage = in.readByte();
        nextPage = tmpNextPage == 0 ? null : tmpNextPage == 1;
        if (in.readByte() == 0) {
            pageCount = null;
        } else {
            pageCount = in.readInt();
        }
        sort = in.readString();
        direction = in.readString();
        if (in.readByte() == 0) {
            limit = null;
        } else {
            limit = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (page == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(page);
        }
        if (current == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(current);
        }
        if (count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(count);
        }
        if (perPage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(perPage);
        }
        dest.writeByte((byte) (prevPage == null ? 0 : prevPage ? 1 : 2));
        dest.writeByte((byte) (nextPage == null ? 0 : nextPage ? 1 : 2));
        if (pageCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pageCount);
        }
        dest.writeString(sort);
        dest.writeString(direction);
        if (limit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(limit);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Paging> CREATOR = new Creator<Paging>() {
        @Override
        public Paging createFromParcel(Parcel in) {
            return new Paging(in);
        }

        @Override
        public Paging[] newArray(int size) {
            return new Paging[size];
        }
    };

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPrevPage(Boolean prevPage) {
        this.prevPage = prevPage;
    }

    public Boolean getPrevPage() {
        return prevPage;
    }

    public void setNextPage(Boolean nextPage) {
        this.nextPage = nextPage;
    }

    public Boolean getNextPage() {
        return nextPage;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }
}
