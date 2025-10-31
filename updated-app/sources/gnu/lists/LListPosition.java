package gnu.lists;

class LListPosition extends ExtPosition {
    Object xpos;

    public LListPosition(LListPosition old) {
        this.sequence = old.sequence;
        this.ipos = old.ipos;
        this.xpos = old.xpos;
    }

    public SeqPosition copy() {
        return new LListPosition(this);
    }

    public LListPosition(LList seq, int index, boolean isAfter) {
        set(seq, index, isAfter);
    }

    public void set(LList seq, int index, boolean isAfter) {
        int skip;
        this.sequence = seq;
        this.ipos = (index << 1) | isAfter ? 1 : 0;
        int skip2 = index;
        if (isAfter) {
            skip = skip2 - 2;
        } else {
            skip = skip2 - 1;
        }
        if (skip >= 0) {
            Object p = seq;
            while (true) {
                skip--;
                if (skip >= 0) {
                    p = ((Pair) p).cdr;
                } else {
                    this.xpos = p;
                    return;
                }
            }
        } else {
            this.xpos = null;
        }
    }

    public void set(AbstractSequence seq, int index, boolean isAfter) {
        set((LList) seq, index, isAfter);
    }

    public boolean hasNext() {
        if (this.xpos != null) {
            Object next = ((Pair) this.xpos).cdr;
            if ((this.ipos & 1) > 0) {
                next = ((Pair) next).cdr;
            }
            if (next != LList.Empty) {
                return true;
            }
            return false;
        } else if ((this.ipos >> 1) == 0) {
            if (this.sequence != LList.Empty) {
                return true;
            }
            return false;
        } else if (((Pair) this.sequence).cdr != LList.Empty) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasPrevious() {
        return (this.ipos >>> 1) != 0;
    }

    public Pair getNextPair() {
        Object next;
        if ((this.ipos & 1) > 0) {
            if (this.xpos == null) {
                Object next2 = this.sequence;
                next = next2;
                if ((this.ipos >> 1) != 0) {
                    next = ((Pair) next2).cdr;
                }
            } else {
                Pair pair = (Pair) ((Pair) this.xpos).cdr;
                Pair pair2 = pair;
                next = pair.cdr;
            }
        } else if (this.xpos == null) {
            next = this.sequence;
        } else {
            next = ((Pair) this.xpos).cdr;
        }
        if (next == LList.Empty) {
            return null;
        }
        return (Pair) next;
    }

    public Object getNext() {
        Pair pair = getNextPair();
        return pair == null ? LList.eofValue : pair.car;
    }

    public void setNext(Object value) {
        getNextPair().car = value;
    }

    public Pair getPreviousPair() {
        int isAfter = this.ipos & 1;
        Object p = this.xpos;
        if (isAfter > 0) {
            p = p == null ? this.sequence : ((Pair) p).cdr;
        } else if (p == null) {
            return null;
        }
        if (p == LList.Empty) {
            return null;
        }
        return (Pair) p;
    }

    public Object getPrevious() {
        Pair pair = getPreviousPair();
        return pair == null ? LList.eofValue : pair.car;
    }

    public void setPrevious(Object value) {
        getPreviousPair().car = value;
    }

    public int nextIndex() {
        return this.ipos >> 1;
    }

    public boolean gotoNext() {
        boolean isAfter = (this.ipos & 1) != 0;
        int i = this.ipos;
        Object xp = this.xpos;
        if (xp != null) {
            if (isAfter) {
                xp = ((Pair) xp).cdr;
            }
            if (((Pair) xp).cdr == LList.Empty) {
                return false;
            }
            this.xpos = xp;
            this.ipos = (this.ipos | 1) + 2;
        } else if ((this.ipos >> 1) != 0) {
            AbstractSequence abstractSequence = this.sequence;
            if (((Pair) abstractSequence).cdr == LList.Empty) {
                return false;
            }
            this.ipos = 5;
            this.xpos = abstractSequence;
        } else if (this.sequence == LList.Empty) {
            return false;
        } else {
            this.ipos = 3;
        }
        return true;
    }

    public boolean gotoPrevious() {
        if ((this.ipos >>> 1) == 0) {
            return false;
        }
        if ((this.ipos & 1) != 0) {
            this.ipos -= 3;
            return true;
        }
        set((LList) this.sequence, nextIndex() - 1, false);
        return true;
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("LListPos[");
        sbuf.append("index:");
        sbuf.append(this.ipos);
        if (isAfter()) {
            sbuf.append(" after");
        }
        if (this.position >= 0) {
            sbuf.append(" position:");
            sbuf.append(this.position);
        }
        sbuf.append(']');
        return sbuf.toString();
    }
}
