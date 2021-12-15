package com.example.fantasticshop.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.example.fantasticshop.HomeActivity;
import com.example.fantasticshop.MainActivity;
import com.example.fantasticshop.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class AddItemFragment extends Fragment {
    private Button loadImageBtn;
    private PreviewView previewView;

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    public AddItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        //retrieve components by id
        loadImageBtn = view.findViewById(R.id.btn_upload);
        previewView = view.findViewById(R.id.previewView);

        //check the phone's camera

        // When clicked, it open phone's image galery
        loadImageBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                //check permissions
                if (hasCameraPermission()) {
                    enableCamera();
                } else {
                    requestPermission();
                }

            }

            private void enableCamera() {
                cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
                cameraProviderFuture.addListener(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                            bindImageAnalysis(cameraProvider);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }, ContextCompat.getMainExecutor(getContext()));


            }

            private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {

                ImageAnalysis imageAnalysis =
                        new ImageAnalysis.Builder().setTargetResolution(new Size(1280, 720))
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(getContext()), image -> image.close());

                OrientationEventListener orientationEventListener = new OrientationEventListener(getContext()) {
                    @Override
                    public void onOrientationChanged(int orientation) {
//                        Toast.makeText(getContext(), "Orientation Enabled", Toast.LENGTH_SHORT).show();
                    }
                };

//                orientationEventListener.enable();
                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                cameraProvider.bindToLifecycle((LifecycleOwner) getContext(), cameraSelector,
                        imageAnalysis, preview);
            }


            private void requestPermission() {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        CAMERA_PERMISSION,
                        CAMERA_REQUEST_CODE
                );
            }

            private boolean hasCameraPermission() {
                return ContextCompat.checkSelfPermission(
                        getContext(),
                        Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED;
            }
        });

        return view;
    }


}
