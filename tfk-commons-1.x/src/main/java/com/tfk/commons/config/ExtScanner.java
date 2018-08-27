package com.tfk.commons.config;

import org.hibernate.boot.archive.scan.internal.StandardScanner;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;
import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.hibernate.boot.archive.scan.spi.ScanParameters;
import org.hibernate.boot.archive.scan.spi.ScanResult;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ExtScanner extends StandardScanner {

    @Override
    public ScanResult scan(ScanEnvironment environment, ScanOptions options, ScanParameters parameters) {

        ScanResult result =  super.scan(environment, options, parameters);

        return result;
    }
}