/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/peterh/proj/eclipse/Nagare/src/com/giantrabbit/nagare/INagareService.aidl
 */
package com.giantrabbit.nagare;
import java.lang.String;
import android.os.RemoteException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;
import android.os.Parcel;
public interface INagareService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.giantrabbit.nagare.INagareService
{
private static final java.lang.String DESCRIPTOR = "com.giantrabbit.nagare.INagareService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an INagareService interface,
 * generating a proxy if needed.
 */
public static com.giantrabbit.nagare.INagareService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
com.giantrabbit.nagare.INagareService in = (com.giantrabbit.nagare.INagareService)obj.queryLocalInterface(DESCRIPTOR);
if ((in!=null)) {
return in;
}
return new com.giantrabbit.nagare.INagareService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_download:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.download(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_errors:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.errors();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_file_name:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.file_name();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_position:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.position();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_stop:
{
data.enforceInterface(DESCRIPTOR);
this.stop();
reply.writeNoException();
return true;
}
case TRANSACTION_state:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.state();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.giantrabbit.nagare.INagareService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public void download(java.lang.String url) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(url);
mRemote.transact(Stub.TRANSACTION_download, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public java.lang.String errors() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_errors, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.lang.String file_name() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_file_name, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public long position() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_position, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void stop() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stop, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int state() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_state, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_download = (IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_errors = (IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_file_name = (IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_position = (IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_stop = (IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_state = (IBinder.FIRST_CALL_TRANSACTION + 5);
}
public void download(java.lang.String url) throws android.os.RemoteException;
public java.lang.String errors() throws android.os.RemoteException;
public java.lang.String file_name() throws android.os.RemoteException;
public long position() throws android.os.RemoteException;
public void stop() throws android.os.RemoteException;
public int state() throws android.os.RemoteException;
}
