package com.navarna.computerdb.dto;

import javax.validation.constraints.Min;
import com.navarna.computerdb.validator.VerificationNavigation;

@VerificationNavigation
public class NavigationDashboardDTO {
    @Min(1)
    private int page = 1;
    private int nbElement = 10;
    private String search = "";
    private String type = "id";
    private String order = "ASC";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNbElement() {
        return nbElement;
    }

    public void setNbElement(int nbElement) {
        this.nbElement = nbElement;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((search == null) ? 0 : search.hashCode());
        result = prime * result + nbElement;
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        result = prime * result + page;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NavigationDashboardDTO)) {
            return false;
        }
        NavigationDashboardDTO other = (NavigationDashboardDTO) obj;
        if (search == null) {
            if (other.search != null) {
                return false;
            }
        } else if (!search.equals(other.search)) {
            return false;
        }
        if (nbElement != other.nbElement) {
            return false;
        }
        if (order == null) {
            if (other.order != null) {
                return false;
            }
        } else if (!order.equals(other.order)) {
            return false;
        }
        if (page != other.page) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NavigationDashboard [page=" + page + ", nbElement=" + nbElement + ", search=" + search + ", type="
                + type + ", order=" + order + "]";
    }

}
