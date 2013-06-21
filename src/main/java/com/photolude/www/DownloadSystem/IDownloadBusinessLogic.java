package com.photolude.www.DownloadSystem;

import com.photolude.www.WebClient.IHttpSessionClient;

public interface IDownloadBusinessLogic {
	boolean initialize(String imageIdsString, String destinationDirectory, String domain, String sessionCookie, String authentication, String downloadPage);
	int getTotalImages();
	void start();
	void addListener(IDownloadEventListener listener);
	IHttpSessionClient getHttpClient();
}
