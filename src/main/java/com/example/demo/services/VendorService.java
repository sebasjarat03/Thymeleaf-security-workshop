package com.example.demo.services;

import com.example.demo.model.prchasing.Vendor;

public interface VendorService {
	public boolean save(Vendor vendor);

	public boolean edit(Vendor vendor, Integer businessentityid);

}
