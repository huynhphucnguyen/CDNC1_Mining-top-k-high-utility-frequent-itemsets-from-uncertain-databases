package TUFP;
//Generic type T1 T2: name, ep
public class topK <T1, T2>{
    T1 namePattern;
    T2 expSupOfPattern;

    public topK(T1 namePattern, T2 expSupOfPattern){
        this.namePattern = namePattern;
        this.expSupOfPattern = expSupOfPattern;
    }

    public T1 getNamePattern() {
        return namePattern;
    }

    public T2 getExpSupOfPattern(){
        return expSupOfPattern;
    }

    @Override
    public String toString(){
        return namePattern + " : " + expSupOfPattern + "\n";
    }
}

// This is class have the info of top-K patterns. 