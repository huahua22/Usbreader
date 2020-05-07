//
// Created by Administrator on 2018/12/29.
//

#ifndef F4_MDS_F4_MDS_H
#define F4_MDS_F4_MDS_H
#include <jni.h>
extern "C" {
long Reader_Init(JNIEnv *env, jobject deviceConnection, jobject usbEpIn, jobject usbEPOut);
long ICC_Reader_PowerOn(JNIEnv *env, unsigned char slot, unsigned char *atr);
long ICC_Reader_PowerOff(JNIEnv *env, unsigned char slot);
long ICC_Reader_GetStatus(JNIEnv *env, unsigned char slot);
long ICC_Reader_Application(JNIEnv *env, unsigned char slot, long command_len, unsigned char *apdu,
                            unsigned char *respond_apdu);
long ICC_Reader_Libinfo(JNIEnv *env, char *info);
long ICC_Reader_GetDevID(JNIEnv *env, char *device_id);
long Activation_Rfcard(JNIEnv *env, long time_out, unsigned char *card_type, long *uid_len,
                       unsigned char *card_uid, long *atr_len, unsigned char *atr);
long Application_Rfcard(JNIEnv *env, long apdu_len, unsigned char *apdu, long *respond_len,
                        unsigned char *respond_apdu);
long Rf_Set_Type(JNIEnv *env, unsigned char mode);
long Syn_Find_Card(JNIEnv *env);
long Syn_Select_Card(JNIEnv *env);
long Syn_Read_Card(JNIEnv *env, unsigned char *id_data);
long Syn_Get_Data(JNIEnv *env, unsigned char *id_data);
long Get_QRCode(JNIEnv *env, unsigned char voice_mode, unsigned char timeout, unsigned char *data);
long Lib_Version(char *Info);
long Test_Beep(JNIEnv *env);
}
#endif //F4_MDS_F4_MDS_H
