Pod::Spec.new do |s|
  s.name                    = 'StarterCore'
  s.version                 = '0.0.1'
  s.summary                 = '${summary}'
  s.author                  = { 'Colton Nohelty' => 'noheltycolton@gmail.com' }
  s.homepage                = 'https://github.com/noheltcj'
  s.license                 = { :type => 'MIT', :file => '../LICENSE' }
  s.platform                = :ios
  s.requires_arc            = true
  s.module_name             = "EX"
  s.source                  = { :git => 'https://github.com/noheltcj/multiplatform-kotlin-hackathon-starter.git', :tag => '0.0.1' }
  s.source_files            = 'build/release/StarterCore.framework/Headers/*.h'
# s.resource                = 'build/release/StarterCore.framework/Info.plist'
  s.public_header_files     = 'build/release/StarterCore.framework/Headers/*.h'
  s.ios.deployment_target   = '11.0'
  s.ios.vendored_frameworks = 'build/release/StarterCore.framework'
end
