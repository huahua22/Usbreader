#include <jni.h>
#include <string>
#include <F4_MDS.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_stringFromJNI(JNIEnv *env, jclass type) {

    // TODO

    return env->NewStringUTF("Hello from C++");
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_Reader_1Init(JNIEnv *env, jclass type,
                                                      jobject deviceConnection, jobject usbEpIn,
                                                      jobject usbEPOut) {

    jlong ret;
    ret = Reader_Init(env, deviceConnection, usbEpIn, usbEPOut);
    return ret;

}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_Test_1Beep(JNIEnv *env, jclass type) {

    jlong ret;
    ret = Test_Beep(env);
    return ret;

}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_ICC_1Reader_1PowerOn(JNIEnv *env, jclass type, jbyte slot,
                                                              jbyteArray atr_) {
    jlong ret;
    jbyte *atr = env->GetByteArrayElements(atr_, NULL);
    unsigned char *uc_atr = (unsigned char *) atr;
    ret = ICC_Reader_PowerOn(env, slot, uc_atr);

    env->ReleaseByteArrayElements(atr_, atr, 0);
    return ret;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_ICC_1Reader_1PowerOff(JNIEnv *env, jclass type,
                                                               jbyte slot) {

    jlong ret;
    ret = ICC_Reader_PowerOff(env, slot);
    return ret;

}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_ICC_1Reader_1GetStatus(JNIEnv *env, jclass type,
                                                                jbyte slot) {

    jlong ret;
    ret = ICC_Reader_GetStatus(env, slot);
    return ret;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_ICC_1Reader_1Application(JNIEnv *env, jclass type,
                                                                  jbyte slot, jlong command_len,
                                                                  jbyteArray apdu_,
                                                                  jbyteArray respond_apdu_) {
    jlong ret;
    jbyte *apdu = env->GetByteArrayElements(apdu_, NULL);
    jbyte *respond_apdu = env->GetByteArrayElements(respond_apdu_, NULL);
    unsigned char *uc_apdu = (unsigned char *) apdu;
    unsigned char *uc_respond_apdu = (unsigned char *) respond_apdu;

    ret = ICC_Reader_Application(env, slot, command_len, uc_apdu, uc_respond_apdu);

    env->ReleaseByteArrayElements(apdu_, apdu, 0);
    env->ReleaseByteArrayElements(respond_apdu_, respond_apdu, 0);
    return ret;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_ICC_1Reader_1GetDevID(JNIEnv *env, jclass type,
                                                               jbyteArray device_id_) {
    jlong ret;
    jbyte *device_id = env->GetByteArrayElements(device_id_, NULL);
    char *uc_device_id = (char *) device_id;

    ret = ICC_Reader_GetDevID(env, uc_device_id);

    env->ReleaseByteArrayElements(device_id_, device_id, 0);
    return ret;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_Lib_1Version(JNIEnv *env, jclass type, jbyteArray Info_) {
    jlong ret;
    jbyte *info = env->GetByteArrayElements(Info_, NULL);
    char *uc_info = (char *) info;

    ret = ICC_Reader_Libinfo(env, uc_info);

    env->ReleaseByteArrayElements(Info_, info, 0);
    return ret;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_Syn_1Read_1Card(JNIEnv *env, jclass type,
                                                         jbyteArray cardInfo_) {
    jlong ret;
    jbyte *cardInfo = env->GetByteArrayElements(cardInfo_, NULL);
    unsigned char *uc_info = (unsigned char *) cardInfo;
    ret = Syn_Read_Card(env, uc_info);
    env->ReleaseByteArrayElements(cardInfo_, cardInfo, 0);
    return ret;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_Syn_1Find_1Card(JNIEnv *env, jclass type) {

    jlong ret;
    ret = Syn_Find_Card(env);
    return ret;

}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_Syn_1Get_1Card(JNIEnv *env, jclass type,
                                                        jbyteArray cardInfo_) {
    jlong ret;
    jbyte *cardInfo = env->GetByteArrayElements(cardInfo_, NULL);
    unsigned char *uc_info = (unsigned char *) cardInfo;
    ret = Syn_Get_Data(env, uc_info);
    // TODO
    env->ReleaseByteArrayElements(cardInfo_, cardInfo, 0);
    return ret;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_xwr_usbreader_dev_usbapi_UsbApi_Syn_1Select_1Card(JNIEnv *env, jclass type) {

    jlong ret;
    ret = Syn_Select_Card(env);
    return ret;

}