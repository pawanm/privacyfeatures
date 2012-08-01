package com.android.phoneagent.listeners;

public interface ICallBack<T, U>
{
    public void success(T result);

    public void failure(U result);
}