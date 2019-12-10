package com.jplus.manyfunction.ui.fragment

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.textfield.TextInputEditText
import com.jplus.manyfunction.R
import com.jplus.manyfunction.contract.TestContract
import com.jplus.manyfunction.ui.activity.DownloadListActivity
import com.nice.baselibrary.base.adapter.NiceAdapter
import com.nice.baselibrary.base.common.Constant
import com.nice.baselibrary.base.listener.NotDoubleOnClickListener
import com.nice.baselibrary.base.net.download.NiceDownloadListener
import com.nice.baselibrary.base.ui.BaseFragment
import com.nice.baselibrary.base.utils.LogUtils
import com.nice.baselibrary.base.utils.createDialog
import com.nice.baselibrary.base.utils.getAlertDialog
import com.nice.baselibrary.base.utils.showNormalToast
import com.nice.baselibrary.base.vo.AppInfo
import com.nice.baselibrary.widget.BaseCircleProgress
import com.nice.baselibrary.widget.NiceTextView
import com.nice.baselibrary.widget.dialog.NiceAlertDialog
import com.nice.baselibrary.widget.dialog.NiceDialog
import kotlinx.android.synthetic.main.fragment_test.*
import java.io.File

/**
 * @author JPlus
 * @date 2019/5/8.
 */
class TestFragment : BaseFragment(), TestContract.View {

    private var mPresenter: TestContract.Presenter? = null

    override fun getInitView(view: View?, bundle: Bundle?) {

    }

    override fun getFragActivity(): Activity? {
        return this.activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter?.startPermissionTest()
    }

    override fun bindListener() {
        LogUtils.d("Fragment bindListener")
        btn_test_patch?.setOnClickListener(object : NotDoubleOnClickListener() {
            override fun notDoubleOnClick(view: View) {
                mPresenter?.getPatchDownLoadUrl()
            }
        })

        btn_upload_pic?.setOnClickListener(object : NotDoubleOnClickListener() {
            override fun notDoubleOnClick(view: View) {
                LogUtils.d("btn_upload_pic.setOnClickListener")
                mPresenter?.startPhotoTest()
            }
        })

        btn_app_infos?.setOnClickListener(object : NotDoubleOnClickListener() {
            override fun notDoubleOnClick(view: View) {
                LogUtils.d("btn_app_infos.setOnClickListener")
                mPresenter?.getAppInfos()
            }
        })
        btn_app_login?.setOnClickListener(object : NotDoubleOnClickListener() {
            override fun notDoubleOnClick(view: View) {
                LogUtils.d("btn_app_login.setOnClickListener")
                showLogin()
            }

        })
        btn_app_video?.setOnClickListener(object : NotDoubleOnClickListener() {
            override fun notDoubleOnClick(view: View) {
                mPresenter?.playVideo("")
            }
        })
        btn_app_share?.setOnClickListener(object : NotDoubleOnClickListener() {
            override fun notDoubleOnClick(view: View) {
                mPresenter?.share(File(Constant.Path.ROOT_DIR, "技术支持中心-AndroidSDK-蒋鹏(季度工作报告).pptx"))
            }
        })
        btn_app_refresh?.setOnClickListener(object : NotDoubleOnClickListener() {
            override fun notDoubleOnClick(view: View) {
                mPresenter?.refreshLoadView()
            }
        })
    }


    override fun showNotPermissionView(content: String) {
        this.activity?.createDialog(NiceDialog.DIALOG_NORMAL)?.setTitle("关于权限")?.setMessage(content)?.setCanceled(true)?.setCancel("取消", object : NiceDialog.DialogClickListener {
            override fun onClick() {
            }
        })?.setConfirm("去设置", object : NiceDialog.DialogClickListener {
            override fun onClick() {
//                        JPermissionsUtils.instance.startActivityToSetting(context as Activity)
            }
        })?.show()

    }

    override fun showUploadPic() {
        //照相、相片剪裁上传、显示demo
        this.activity?.let {
            it.getAlertDialog()
                    .setBackgroundRes(R.drawable.bg_normal_dialog_view)
                    .setLayoutRes(R.layout.view_photo_dialog)
                    .setCancelable(true)
                    .setTag("newDialog")
                    .setScreenHeightPercent(it, 0.2f)
                    .setScreenWidthPercent(it, 1.0f)
                    .setAnimationRes(R.style.NiceDialogAnim)
                    .setGravity(Gravity.BOTTOM)
                    .setDimAmount(0.0f)
                    .setBindViewListener(object : NiceAlertDialog.OnBindViewListener {
                        override fun onBindView(viewHolder: NiceAdapter.VH) {
                            viewHolder.getView<NiceTextView>(R.id.ntv_photo_dialog_camera).text = "相机"
                            viewHolder.getView<NiceTextView>(R.id.ntv_photo_dialog_photo).text = "照片"
                            viewHolder.getView<NiceTextView>(R.id.ntv_photo_dialog_cancel).text = "取消"

                        }
                    })
                    .addClickedId(R.id.ntv_photo_dialog_camera, R.id.ntv_photo_dialog_photo, R.id.ntv_photo_dialog_cancel)
                    .setViewClickListener(object : NiceAlertDialog.OnViewClickListener {
                        override fun onClick(viewHolder: NiceAdapter.VH, view: View, dialog: NiceAlertDialog) {
                            dialog.dismiss()
                            mPresenter?.checkToCameraOrPhoto(view, dialog)
                        }
                    })

                    .setKeyListener(DialogInterface.OnKeyListener { _, _, _ -> false })
                    .create()
                    .show()
        }
    }

    override fun uploadResultView(url: String?) {
        Glide.with(this).load(url)
                .placeholder(R.drawable.loading_pic)//占位图
                .error(R.drawable.loading_error)//加载错误图
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)//取消缓存功能
                .override(300, 300)//设置尺寸
                .centerCrop()//中心裁剪
                .transform(CircleCrop())//圆形剪裁
                .into(img_head_view)
    }

    override fun showAppInfo(infos: MutableList<AppInfo>) {
        this.activity?.let {
            it.getAlertDialog()
                    .setLayoutRes(R.layout.list_dialog_test)
                    .setCancelable(true)
                    .setTag("newDialog")
                    .setScreenHeightPercent(it, 0.8f)
                    .setScreenWidthPercent(it, 0.8f)
                    .setAnimationRes(R.style.NiceDialogAnim)
                    .setGravity(Gravity.CENTER)
                    .setDimAmount(0.0f)
                    .setListRes(R.id.recycler_test, LinearLayoutManager.VERTICAL)
                    .setAdapter(object : NiceAdapter<AppInfo>(infos) {
                        override fun getLayout(viewType: Int): Int {
                            return R.layout.app_info_view
                        }

                        override fun convert(holder: VH, item: AppInfo, position: Int, payloads: MutableList<Any>?) {
                            holder.getView<TextView>(R.id.tv_app_name).text = item.appName
                            holder.getView<TextView>(R.id.tv_package_name).text = item.packageName
//                            holder.getView<TextView>(R.id.tv_app_size).text = item.signMd5
                        }
                    })
                    .setListItemClickListener(object : NiceAdapter.ItemClickListener {
                        override fun setItemClick(holder: NiceAdapter.VH, position: Int) {
                            it.showNormalToast(infos[position].appName)
                        }

                        override fun setItemLongClick(holder: NiceAdapter.VH, position: Int): Boolean {
                            it.showNormalToast(infos[position].signMd5)
                            return true
                        }
                    })
                    .create()
                    .show()

        }
    }

    override fun showPatchDownLoad() {
        this.activity?.let {
            NiceAlertDialog.Builder(it.supportFragmentManager.beginTransaction())
                    .setLayoutRes(R.layout.view_patch_dialog)
                    .setCancelable(true)
                    .setTag("newDialog")
                    .setScreenHeightPercent(it, 0.4f)
                    .setScreenWidthPercent(it, 0.8f)
                    .setAnimationRes(R.style.NiceDialogAnim)
                    .setGravity(Gravity.CENTER)
                    .setDimAmount(0.0f)
                    .addClickedId(R.id.btn_patch_download)
                    .setViewClickListener(object : NiceAlertDialog.OnViewClickListener {
                        override fun onClick(viewHolder: NiceAdapter.VH, view: View, dialog: NiceAlertDialog) {
                            when (view.id) {
                                R.id.btn_patch_download -> {
                                    LogUtils.d("btn_patch_download.setOnClickListener")
                                    val dirPath = File(Constant.Path.ROOT_DIR, Constant.Path.PATCH_DEX_DIR).absolutePath
                                    mPresenter?.downLoadPatch("http://192.168.11.175:8000/file/upload/class2.dex", dirPath, object : NiceDownloadListener {
                                        override fun update(read: Long, count: Long, done: Boolean) {
                                            viewHolder.getView<BaseCircleProgress>(R.id.ncp_download_patch).loading(String.format("%.1f", read * 100.0 / count).toDouble())
                                        }

                                        override fun downloadSuccess() {
                                            viewHolder.getView<TextView>(R.id.tv_upload_content).text = "下载结束请重启App"
                                            viewHolder.getView<BaseCircleProgress>(R.id.ncp_download_patch).success()
                                        }

                                        override fun downloadFailed(e: Throwable) {
                                            e.printStackTrace()
                                            view.findViewById<BaseCircleProgress>(R.id.ncp_download_patch)?.failed()
                                        }

                                        override fun downloadCancel() {
                                            view.findViewById<BaseCircleProgress>(R.id.ncp_download_patch)?.cancel()
                                        }

                                    })
                                }
                            }
                        }

                    })
                    .create()
                    .show()
        }
    }

    override fun showLogin() {
//        throw RuntimeException("Is RuntimeException.")
        this.activity?.let {
            NiceAlertDialog.Builder(it.supportFragmentManager.beginTransaction())
                    .setLayoutRes(R.layout.view_login_dialog)
                    .setCancelable(true)
                    .setTag("newDialog")
                    .setScreenHeightPercent(it, 0.5f)
                    .setScreenWidthPercent(it, 0.8f)
                    .setAnimationRes(R.style.NiceDialogAnim)
                    .setGravity(Gravity.CENTER)
                    .setDimAmount(0.0f)
                    .addClickedId(R.id.btn_login_login)
                    .setViewClickListener(object : NiceAlertDialog.OnViewClickListener {
                        override fun onClick(viewHolder: NiceAdapter.VH, view: View, dialog: NiceAlertDialog) {
                            when (view.id) {
                                R.id.btn_login_login -> {
                                    val phone = viewHolder.getView<TextInputEditText>(R.id.input_edit_phone_login).text.toString()
                                    val pwd = viewHolder.getView<TextInputEditText>(R.id.input_edit_password_login).text.toString()
                                    mPresenter?.login(phone, pwd)
                                }
                            }
                        }

                    })
                    .create()
                    .show()
        }
    }

    override fun showLoginResult(result: Boolean, message: String) {

    }

    override fun showVideoView() {
        this.activity?.let {
            startActivity(Intent(it, DownloadListActivity::class.java))
        }
    }

    override fun showRefreshLoadView() {

    }

    override fun showShareView() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_test
    }

    override fun setPresenter(presenter: TestContract.Presenter) {
        mPresenter = presenter
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.subscribe()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.unSubscribe()
        mPresenter = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter?.activityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPresenter?.permissionResult(requestCode, permissions, grantResults)
    }

}