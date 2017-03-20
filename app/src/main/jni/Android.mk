LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := XidingJce

LOCAL_SRC_FILES := \
    hsm_jni_interface.cpp \
    hsm_jni_register.cpp 

LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
