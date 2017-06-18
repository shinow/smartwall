/**
 * SmartWall(2013) */
package link.smartwall.grid.controller;

/**
 * 
 * 分页对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-3-11 lexloo
 * </pre>
 */
public class PagerInfo {

    /**
     * 改变这个，当每页大小超过 MAX_FETCH_SIZE 时，这个将是默认的 fetchSize
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 页码
     */
    private int pageNumber = 1;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int pageCount = -1;
    /**
     * 总记录数
     */
    private int recordCount;

    /**
     * 构造函数
     */
    public PagerInfo() {
        pageNumber = 1;
        pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * 构造函数
     * 
     * @param pageNumber 页
     * @param pageSize 页记录数
     */
    public PagerInfo(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber < 1 ? 1 : pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 重新设置总页数，设置总页数为-1
     * 
     * @return 当前对象
     */
    public PagerInfo resetPageCount() {
        pageCount = -1;

        return this;
    }

    /**
     * 获取总页数
     * 
     * @return 总页数
     */
    public int getPageCount() {
        if (pageCount < 0) {
            pageCount = (int) Math.ceil((double) recordCount / pageSize);
        }

        return pageCount;
    }

    /**
     * 获取页码
     * 
     * @return 页码
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * 获取每页记录数
     * 
     * @return 每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取总记录数
     * 
     * @return 总记录数
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * 设置页数
     * 
     * @param pn 页数
     * @return 当前对象
     */
    public PagerInfo setPageNumber(int pn) {
        pageNumber = pn;

        return this;
    }

    /**
     * 设置每页记录数
     * 
     * @param pageSize 每页记录数
     * @return 当前对象
     */
    public PagerInfo setPageSize(int pageSize) {
        this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;

        return resetPageCount();
    }

    /**
     * 设置总记录数
     * 
     * @param recordCount 总记录数
     * @return 当前对象
     */
    public PagerInfo setRecordCount(int recordCount) {
        this.recordCount = recordCount > 0 ? recordCount : 0;
        this.pageCount = (int) Math.ceil((double) recordCount / pageSize);
        this.pageNumber = Math.min(this.pageNumber, this.pageCount);

        return this;
    }

    @Override
    public String toString() {
        return String.format("size: %d, total: %d, page: %d/%d", pageSize, recordCount, pageNumber, this.getPageCount());
    }

    /**
     * 是否是第一页
     * 
     * @return true/false
     */
    public boolean isFirst() {
        return pageNumber == 1;
    }

    /**
     * 是否最后一页
     * 
     * @return true/false
     */
    public boolean isLast() {
        if (pageCount == 0) {
            return true;
        }

        return pageNumber == pageCount;
    }
}
