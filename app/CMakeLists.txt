cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
include_directories(./src/main/head)
SET(DIR_SRC ./src/main/cpp)
FOREACH (next_ITEM ${DIR_SRC})
    aux_source_directory(${next_ITEM} SRC_INCLUDE)
    list(APPEND ALL_SRC ${SRC_INCLUDE})
ENDFOREACH (next_ITEM ${DIR_SRC})
#导入第三方依赖库
add_library(
        F4_MDS
        SHARED
        IMPORTED
)
set_target_properties(
        F4_MDS
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libF4_MDS.so
)


find_library( # Sets the name of the path variable.
        log-lib
        log)

add_library( # Sets the name of the library.
        cp
        SHARED
        ${ALL_SRC})
target_link_libraries( # Specifies the target library.
        cp
        F4_MDS
        ${log-lib})