package com.example.dp.objects

import android.content.Context
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.util.Log
import java.io.*


class PdfDocumentAdapter(var context: Context, var path: String) :
    PrintDocumentAdapter() {
    override fun onLayout(
        oldAttributes: PrintAttributes,
        printAttributes1: PrintAttributes,
        cancellationSignal: CancellationSignal,
        layoutResultCallback: LayoutResultCallback,
        extras: Bundle
    ) {
        if (cancellationSignal.isCanceled) layoutResultCallback.onLayoutCancelled() else {
            val builder = PrintDocumentInfo.Builder("file name")
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build()
            layoutResultCallback.onLayoutFinished(
                builder.build(),
                printAttributes1 != printAttributes1
            )
        }
    }

    override fun onWrite(
        p0: Array<out android.print.PageRange>?,
        p1: ParcelFileDescriptor?,
        p2: CancellationSignal?,
        p3: WriteResultCallback?
    ) {
        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {
            val file = File(path)
            `in` = FileInputStream(file)
            if (p1 != null) {
                out = FileOutputStream(p1.fileDescriptor)
            }
            val buff = ByteArray(16384)
            var size: Int
            if (p2 != null) {
                while (`in`.read(buff).also { size = it } >= 0 && !p2.isCanceled) {
                    out?.write(buff, 0, size)
                }
            }
            if (p2 != null) {
                if (p2.isCanceled) p3?.onWriteCancelled() else {
                    p3?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
                }
            }
        } catch (e: Exception) {
            p3?.onWriteFailed(e.message)
            Log.e("DP", e.message!!)
            e.printStackTrace()
        } finally {
            try {
                `in`?.close()
                out?.close()
            } catch (ex: IOException) {
                Log.e("DP", "" + ex.message)
            }
        }
    }


}