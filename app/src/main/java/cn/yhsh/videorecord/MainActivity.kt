package cn.yhsh.videorecord

import android.hardware.Camera
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File

/**
 * 记得动态申请相机权限
 * @author xiayiye5
 */
class MainActivity : AppCompatActivity() {
    private lateinit var tvShowVideo: TextureView
    private lateinit var btRecord: Button
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var camera: Camera
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvShowVideo = findViewById(R.id.tv_show_video)
        btRecord = findViewById(R.id.bt_record)
        btRecord.setOnClickListener {
            if (btRecord.text.toString() == "录制") {
                camera = Camera.open()
                //设置录制界面的方向
                camera.setDisplayOrientation(90)
                camera.unlock()
                mediaRecorder = MediaRecorder()
                //开始录制
                btRecord.text = "停止"
                mediaRecorder.setCamera(camera)
                // 设置音频采集方式
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                //设置视频的采集方式
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)
                //设置文件的输出格式
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                //设置audio的编码格式
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                //设置video的编码格式
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
                //设置录制的视频编码比特率,越高视频越清晰当前文件也越大
//                mediaRecorder.setVideoEncodingBitRate(1024 * 1024)
//                mediaRecorder.setVideoEncodingBitRate(10 * 1920 * 1080);
                //设置录制的视频帧率,注意文档的说明:
                mediaRecorder.setVideoFrameRate(30);
                //设置录制文件输出目录
                val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DCIM)
                val filePath = "${System.currentTimeMillis()}.mp4"
                val absolutePath = File(externalFilesDir, filePath).absolutePath
                mediaRecorder.setOutputFile(absolutePath)
                //设置分辨率
                mediaRecorder.setVideoSize(640, 480)
                //设置录制方向
                mediaRecorder.setOrientationHint(90)
                mediaRecorder.setPreviewDisplay(Surface(tvShowVideo.surfaceTexture))
                //准备
                mediaRecorder.prepare()
                //开始录制
                mediaRecorder.start()
            } else {
                //停止录制
                btRecord.text = "录制"
                //释放资源
                mediaRecorder.stop()
                mediaRecorder.release()
                camera.stopPreview()
                camera.release()
            }
        }
    }
}