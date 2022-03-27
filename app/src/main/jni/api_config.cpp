#include <jni.h>
#include <string>
#include <jni.h>

std::string SERVER_URL          = "http://s5.ttvmax.com/rest-api/";
std::string API_KEY             = "dzmi6x75opms9ctl1dq9zhw4";
std::string PURCHASE_CODE       = "***********************";


extern "C" jstring
Java_com_code_files_AppConfig_getApiServerUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(SERVER_URL.c_str());
}

extern "C" jstring
Java_com_code_files_AppConfig_getApiKey(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(API_KEY.c_str());
}

extern "C" jstring
Java_com_code_files_AppConfig_getPurchaseCode(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(PURCHASE_CODE.c_str());
}