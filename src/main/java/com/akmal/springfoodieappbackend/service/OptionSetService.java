package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.repository.OptionSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionSetService {
  private final OptionSetRepository optionSetRepository;
}
