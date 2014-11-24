package com.example.java2com;

import java.util.Iterator;


public abstract class COMCollection<T> extends COMInterface implements Iterable<T> {

    protected COMCollection(IDispatch ci) {
        super(ci);
    }

    
    protected abstract T newInstance(IDispatch dispatch); 

    public int getCount() {
        Variant result = ci.getProperty("Count");
        return result.intValue;
    }


    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int idx = 1;
            int count = getCount();

            @Override
            public boolean hasNext() {
                return idx <= count;
            }

            @Override
            public T next() {
                Variant result = ci.getProperty("Item", new Variant(idx));
                idx++;
                return newInstance(result.dispatch);
            }

        };
    }
}