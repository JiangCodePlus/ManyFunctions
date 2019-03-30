package com.nice.baselibrary.base.utils

import android.content.Context
import java.io.*

/**
 * 文件工具类
 * @author JPlus
 * @date 2019/2/22.
 */
class FileUtils {
    companion object {

        /**
         * 文件写入
         * @param file
         * @param stream
         * @param append
         * @return
         */
        fun writeFile(file: File, stream: InputStream, append: Boolean): Boolean {
            var outputStream: OutputStream? = null
            try {
                makeDirs(file)
                outputStream = FileOutputStream(file, append)
                val data = ByteArray(1024)
                var length = 0
                while ({ length = stream.read(data); length }() != -1) {
                    outputStream.write(data, 0, length)
                }
                outputStream.flush()
                return true
            } catch (e: FileNotFoundException) {
                throw RuntimeException("FileNotFoundException occurred. ", e)
            } catch (e: IOException) {
                throw RuntimeException("IOException occurred. ", e)
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close()
                        stream.close()
                    } catch (e: IOException) {
                        throw RuntimeException("IOException occurred. ", e)
                    }

                }
            }
        }


        /**
         * 文件读取
         * @param file
         * @param charsetName
         * @return
         */
        fun readFile2List(file: File, charsetName: String = "UTF-8"): ArrayList<String> {
            val fileContent = ArrayList<String>()
            var reader: BufferedReader? = null
            try {
                val input = InputStreamReader(FileInputStream(file), charsetName)
                reader = BufferedReader(input)
                var line: String? = null
                while ({ line = reader!!.readLine();line }() != null) {
                    fileContent.add(line!!)
                }
                return fileContent
            } catch (e: FileNotFoundException) {
                throw RuntimeException("FileNotFoundException occurred. ", e)
            } catch (e: IOException) {
                throw RuntimeException("IOException occurred. ", e)
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        throw RuntimeException("IOException occurred. ", e)
                    }
                }
            }
    }
        /**
         * 文件读取
         * @param file
         * @param charsetName
         * @return
         */
        fun readFile2List(inputs: InputStream, charsetName: String = "UTF-8"): ArrayList<String> {
            val fileContent = ArrayList<String>()
            var reader: BufferedReader? = null
            try {
                val input = InputStreamReader(inputs, charsetName)
                reader = BufferedReader(input)
                var line: String? = null
                while ({ line = reader!!.readLine();line }() != null) {
                    fileContent.add(line!!)
                }
                return fileContent
            } catch (e: FileNotFoundException) {
                throw RuntimeException("FileNotFoundException occurred. ", e)
            } catch (e: IOException) {
                throw RuntimeException("IOException occurred. ", e)
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        throw RuntimeException("IOException occurred. ", e)
                    }
                }
            }
        }
        /**
         * 文件读取
         * @param file
         * @param charsetName
         * @return
         */
        fun readFile2String(file: File, charsetName: String = "UTF-8"): String {
            var input: FileInputStream? = null
            try {
                input = FileInputStream(file)
                val buffer = ByteArray(input.available())
                input.read(buffer)
                return String(buffer, charset(charsetName))
            } catch (e: FileNotFoundException) {
                throw RuntimeException("FileNotFoundException occurred. ", e)
            } catch (e: IOException) {
                throw RuntimeException("IOException occurred. ", e)
            } finally {
                if (input != null) {
                    try {
                        input.close()
                    } catch (e: IOException) {
                        throw RuntimeException("IOException occurred. ", e)
                    }

                }
            }
        }
    /**
     * 文件读取
     * @param input
     * @param charsetName
     * @return
     */
    fun readFile2String(input: InputStream, charsetName: String = "UTF-8"): String {
        val buffer = ByteArray(input.available())
        input.read(buffer)
        return String(buffer, charset(charsetName))
    }


    /**
     * 私有文件写入
     * @param filePath
     * @param context
     * @param content
     */
    fun writePrivateFile(filePath: String, context: Context, content: String) {
        val output = context.openFileOutput(filePath, Context.MODE_APPEND)
        output.write(content.toByteArray())
        output.close()
    }

    private fun getFolderName(filePath: String): String {
        val filePosition = filePath.lastIndexOf(File.separator)
        return if (filePosition == -1) "" else filePath.substring(0, filePosition)
    }

    private fun makeFile(filePath: String): Boolean {
        val file = File(filePath)
        return if (file.exists()) true else file.createNewFile()
    }

    private fun makeDirs(file: File): Boolean {
        val folderName = getFolderName(file.absolutePath)
        val folder = File(folderName)
        return if (folder.exists() && folder.isDirectory) true else folder.mkdirs()
    }
}
}