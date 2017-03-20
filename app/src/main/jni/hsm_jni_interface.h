#ifndef HSM_JNI_INTERFACE_H_
#define HSM_JNI_INTERFACE_H_

const char* hsm_get_class_name();
JNINativeMethod* hsm_get_methods(int* pCount);

#endif /* HSM_JNI_INTERFACE_H_*/