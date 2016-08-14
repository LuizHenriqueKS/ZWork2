package br.zul.zwork2.string;

/**
 *
 * @author Luiz Henrique
 */
public class ZStringSearchTypeArgs{
        private ZStringSearchResult firstResult;
        private ZStringSearchResult secondResult;
        private boolean removeFirst;
        private boolean removeSecond;
        
        public ZStringSearchTypeArgs(ZStringSearchResult firstResult,ZStringSearchResult secondResult){
            this.firstResult = firstResult;
            this.secondResult = secondResult;
            this.removeFirst = this.removeSecond = false;
        }

        public ZStringSearchResult getFirstResult() {
            return firstResult;
        }
        public void setFirstResult(ZStringSearchResult firstResult) {
            this.firstResult = firstResult;
        }

        public ZStringSearchResult getSecondResult() {
            return secondResult;
        }
        public void setSecondResult(ZStringSearchResult secondResult) {
            this.secondResult = secondResult;
        }

        public boolean isRemoveFirst() {
            return removeFirst;
        }
        public void setRemoveFirst(boolean removeFirst) {
            this.removeFirst = removeFirst;
        }

        public boolean isRemoveSecond() {
            return removeSecond;
        }
        public void setRemoveSecond(boolean removeSecond) {
            this.removeSecond = removeSecond;
        }
        
    }
