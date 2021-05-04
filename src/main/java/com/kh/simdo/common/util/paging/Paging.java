package com.kh.simdo.common.util.paging;

public class Paging {

    // Part 1. 외부로 부터 입력받을 값, 게터세터 필요
    private String type; // 페이징 처리 할 항목
    private int currentPage; //현재페이지
    private int total; //전체게시물 수
    private int cntPerPage; //페이지당 게시물 수
    private int blockCnt; // 한줄에 표시될 블록 개수

    // Part 2.우리가 계산해야하는 값, 세터 필요 없다.
    private int lastPage; // 마지막 페이지, 시작페이지는 어차피 1일거니깐 필요없다
    private int blockStart;// 시작블록
    private int blockEnd; //끝 블록
    private int prev; //이전버튼에 들어갈 값
    private int next; //다음버튼에 들어갈 값

    //쿼리에서 between문에 지정할 rownum 값
    private int queryStart;
    private int queryEnd;

    // Part 1.외부 입력 값은 빌더패턴으로 세터 구현해보자!
    // 6. builder를 받는 생성자 구현
    private Paging(PagingBuilder builder) {
        this.type = builder.type;
        this.currentPage = builder.currentPage;
        this.total = builder.total;
        this.cntPerPage = builder.cntPerPage;
        this.blockCnt = builder.blockCnt;

        calLastPage();
        calBlockStartAndEnd();
        calQueryStartAndEnd();
        calPrevAndNext();
    }

    // Part 2. 우리가 계산할 값 구현
    // a. 마지막페이지 계산 : 외부입력값인 전체게시물 수 / 페이지당 게시물 수
    private void calLastPage() {
        lastPage = (int) Math.ceil((double)total/(double)cntPerPage);
    }

    // b. 블록 시작 끝값 계산 : 현재 페이지 값보다 작은 blockCnt의 배수 중 가장 큰값에 1을 더하면 시작 값
    private void calBlockStartAndEnd() {
        blockStart = ((currentPage -1) / blockCnt) * blockCnt + 1;
        blockEnd = blockStart + blockCnt -1;

        blockStart = blockStart < 1?1:blockStart;
        blockEnd = blockEnd > lastPage?lastPage:blockEnd;

    }

    // c.쿼리 시작, 끝값
    private void calQueryStartAndEnd() {
        queryEnd = currentPage * cntPerPage;
        queryStart = queryEnd - cntPerPage + 1;
    }

    // d.이전, 다음 구현
    private void calPrevAndNext() {
        prev = currentPage == 1?currentPage:currentPage-1;
        next = currentPage == lastPage?lastPage:currentPage+1;
    }

    // 2. PagingBuilder를 밖에서 부를수 있게 해줄 클래스 구현
    public static PagingBuilder builder() {
        return new PagingBuilder();
    }

    // 1. static class 만들기
    public static class PagingBuilder{
        //3. 입력받을 값 가져오기
        private String type;
        private int currentPage;
        private int total;
        private int cntPerPage;
        private int blockCnt;

        // 4. 자기자신 반환하는 클래스 생성 (setter 기능)
        public PagingBuilder type(String val) {
            this.type = val;
            return this;
        }

        public PagingBuilder currentPage(int val) {
            this.currentPage = val;
            return this;
        }

        public PagingBuilder total(int val) {
            this.total = val;
            return this;
        }

        public PagingBuilder cntPerPage(int val) {
            this.cntPerPage = val;
            return this;
        }

        public PagingBuilder blockCnt(int val) {
            this.blockCnt = val;
            return this;
        }

        // 5.
        public Paging build() {
            return new Paging(this);
        }
    }



    public String getType() {
        return type;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotal() {
        return total;
    }

    public int getCntPerPage() {
        return cntPerPage;
    }

    public int getBlockCnt() {
        return blockCnt;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getBlockStart() {
        return blockStart;
    }

    public int getBlockEnd() {
        return blockEnd;
    }

    public int getPrev() {
        return prev;
    }

    public int getNext() {
        return next;
    }

    public int getQueryStart() {
        return queryStart;
    }

    public int getQueryEnd() {
        return queryEnd;
    }

}
