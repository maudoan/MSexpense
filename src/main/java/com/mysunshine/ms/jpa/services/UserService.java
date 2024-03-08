package com.mysunshine.ms.jpa.services;

import com.mysunshine.ms.constant.enums.ERole;
import com.mysunshine.ms.filter.JwtUtils;
import com.mysunshine.ms.jpa.CrudService;
import com.mysunshine.ms.jpa.models.Role;
import com.mysunshine.ms.jpa.models.TransactionCategoryChild;
import com.mysunshine.ms.jpa.models.TransactionCategoryParent;
import com.mysunshine.ms.jpa.models.User;
import com.mysunshine.ms.jpa.repositories.RoleRepository;
import com.mysunshine.ms.jpa.repositories.TransactionCategoryParentRepository;
import com.mysunshine.ms.jpa.repositories.UserRepository;
import com.mysunshine.ms.payloads.request.SignupRequest;
import com.mysunshine.ms.payloads.response.MessageResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService extends CrudService<User, Long> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TransactionCategoryParentRepository transactionCategoryParentRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public UserService(UserRepository userRepository) {
        super(User.class);
        this.repository = this.userRepository = userRepository;
    }

    public ResponseEntity<?> register(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setCreated(System.currentTimeMillis());
        user.setCreatedBy(user.getUsername());
        user.setTotalBalance(0L);
        userRepository.save(user);
        String[] arrayCategoryNamesTypeExpense = {"Ăn uống", "Hóa đơn & Tiện ích", "Mua sắm", "Gia đình", "Di chuyển", "Sức Khỏe", "Giáo dục", "Giải trí", "Quà tặng & Quyên góp", "Bảo hiểm", "Đầu tư", "Các chi phí khác", "Tiền chuyển đi", "Trả lãi", "Khoản chi phí chưa phân loại"};
        List<TransactionCategoryParent> transactionCategoryParentList = new ArrayList<>();
        for (String name : arrayCategoryNamesTypeExpense) {
            TransactionCategoryParent transactionCategoryParent = new TransactionCategoryParent();
            switch (name) {
                case "Ăn uống":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Ăn uống");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Hóa đơn & Tiện ích":
                    String[] categoryChild1 = {"Thuê nhà", "Hóa đơn nước", "Hóa đơn điện thoại", "Hóa đơn điện", "Hóa đơn gas", "Hóa đơn TV", "Hóa đơn internet", "Hóa đơn tiện ích khác"};
                    List<TransactionCategoryChild> transactionCategoryChildList = new ArrayList<>();
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Hóa đơn & Tiện ích");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    for (String name1 : categoryChild1) {
                        TransactionCategoryChild transactionCategoryChild1 = new TransactionCategoryChild();
                        transactionCategoryChild1.setName(name1);
                        transactionCategoryChild1.setCreated(System.currentTimeMillis());
                        transactionCategoryChild1.setCreatedBy(user.getUsername());
                        transactionCategoryChild1.setTransactionCategoryParent(transactionCategoryParent);
                        transactionCategoryChild1.setIcon("abc");
                        transactionCategoryChildList.add(transactionCategoryChild1);
                    }
                    transactionCategoryParent.setTransactionCategoryChild(transactionCategoryChildList);
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Mua sắm":
                    String[] categoryChild2 = {"Đồ dùng cá nhân", "Đồ gia dụng", "Làm đẹp"};
                    List<TransactionCategoryChild> transactionCategoryChildList2 = new ArrayList<>();
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Mua sắm");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    for (String name2 : categoryChild2) {
                        TransactionCategoryChild transactionCategoryChild2 = new TransactionCategoryChild();
                        transactionCategoryChild2.setName(name2);
                        transactionCategoryChild2.setCreated(System.currentTimeMillis());
                        transactionCategoryChild2.setCreatedBy(user.getUsername());
                        transactionCategoryChild2.setTransactionCategoryParent(transactionCategoryParent);
                        transactionCategoryChild2.setIcon("abc");
                        transactionCategoryChildList2.add(transactionCategoryChild2);
                    }
                    transactionCategoryParent.setTransactionCategoryChild(transactionCategoryChildList2);
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Gia đình":
                    String[] categoryChild3 = {"Sửa & trang trí nhà", "Dịch vụ gia đình", "Vật nuôi"};
                    List<TransactionCategoryChild> transactionCategoryChildList3 = new ArrayList<>();
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Gia đình");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    for (String name3 : categoryChild3) {
                        TransactionCategoryChild transactionCategoryChild3 = new TransactionCategoryChild();
                        transactionCategoryChild3.setName(name3);
                        transactionCategoryChild3.setCreated(System.currentTimeMillis());
                        transactionCategoryChild3.setCreatedBy(user.getUsername());
                        transactionCategoryChild3.setTransactionCategoryParent(transactionCategoryParent);
                        transactionCategoryChild3.setIcon("abc");
                        transactionCategoryChildList3.add(transactionCategoryChild3);
                    }
                    transactionCategoryParent.setTransactionCategoryChild(transactionCategoryChildList3);
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;

                case "Di chuyển":
                    String[] categoryChild4 = {"Bảo dưỡng xe"};
                    List<TransactionCategoryChild> transactionCategoryChildList4 = new ArrayList<>();
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Gia đình");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    for (String name4 : categoryChild4) {
                        TransactionCategoryChild transactionCategoryChild4 = new TransactionCategoryChild();
                        transactionCategoryChild4.setName(name4);
                        transactionCategoryChild4.setCreated(System.currentTimeMillis());
                        transactionCategoryChild4.setCreatedBy(user.getUsername());
                        transactionCategoryChild4.setTransactionCategoryParent(transactionCategoryParent);
                        transactionCategoryChild4.setIcon("abc");
                        transactionCategoryChildList4.add(transactionCategoryChild4);
                    }
                    transactionCategoryParent.setTransactionCategoryChild(transactionCategoryChildList4);
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Sức khỏe":
                    String[] categoryChild5 = {"Khám sức khỏe", "Thể dục thể thao"};
                    List<TransactionCategoryChild> transactionCategoryChildList5 = new ArrayList<>();
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Gia đình");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    for (String name5 : categoryChild5) {
                        TransactionCategoryChild transactionCategoryChild5 = new TransactionCategoryChild();
                        transactionCategoryChild5.setName(name5);
                        transactionCategoryChild5.setCreated(System.currentTimeMillis());
                        transactionCategoryChild5.setCreatedBy(user.getUsername());
                        transactionCategoryChild5.setTransactionCategoryParent(transactionCategoryParent);
                        transactionCategoryChild5.setIcon("abc");
                        transactionCategoryChildList5.add(transactionCategoryChild5);
                    }
                    transactionCategoryParent.setTransactionCategoryChild(transactionCategoryChildList5);
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Giáo dục":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Giáo dục");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Giải trí":
                    String[] categoryChild6 = {"Dịch vụ trực tuyến", "Vui chơi"};
                    List<TransactionCategoryChild> transactionCategoryChildList6 = new ArrayList<>();
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Gia đình");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    for (String name6 : categoryChild6) {
                        TransactionCategoryChild transactionCategoryChild6 = new TransactionCategoryChild();
                        transactionCategoryChild6.setName(name6);
                        transactionCategoryChild6.setCreated(System.currentTimeMillis());
                        transactionCategoryChild6.setCreatedBy(user.getUsername());
                        transactionCategoryChild6.setTransactionCategoryParent(transactionCategoryParent);
                        transactionCategoryChild6.setIcon("abc");
                        transactionCategoryChildList6.add(transactionCategoryChild6);
                    }
                    transactionCategoryParent.setTransactionCategoryChild(transactionCategoryChildList6);
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Quà tặng & Quyên góp":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Quà tặng & Quyên góp");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Bảo hiểm":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Bảo hiểm");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Đầu tư":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Đầu tư");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Các chi phí khác":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Các chi phí khác");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Tiền chuyển đi":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Tiền chuyển đi");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Trả lãi":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Trả lãi");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Khoản chi chưa phân loại":
                    transactionCategoryParent.setTransactionType(1);
                    transactionCategoryParent.setName("Khoản chi chưa phân loại");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                default:
            }
        }
        String[] arrayCategoryNamesTypeRevenue = {"Lương", "Thu nhập khác", "Tiền chuyển đến", "Thu lãi", "Khoản thu chưa phân loại"};

        for (String name : arrayCategoryNamesTypeRevenue) {
            TransactionCategoryParent transactionCategoryParent = new TransactionCategoryParent();
            switch (name) {
                case "Lương":
                    transactionCategoryParent.setTransactionType(2);
                    transactionCategoryParent.setName("Lương");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Thu nhập khác":
                    transactionCategoryParent.setTransactionType(2);
                    transactionCategoryParent.setName("Thu nhập khác");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Tiền chuyển đến":
                    transactionCategoryParent.setTransactionType(2);
                    transactionCategoryParent.setName("Tiền chuyển đến");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Thu lãi":
                    transactionCategoryParent.setTransactionType(2);
                    transactionCategoryParent.setName("Thu lãi");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                case "Khoản thu chưa phân loại":
                    transactionCategoryParent.setTransactionType(2);
                    transactionCategoryParent.setName("Khoản thu chưa phân loại");
                    transactionCategoryParent.setCreated(System.currentTimeMillis());
                    transactionCategoryParent.setCreatedBy(user.getUsername());
                    transactionCategoryParent.setUser(user);
                    transactionCategoryParent.setIcon("abc");
                    transactionCategoryParentList.add(transactionCategoryParent);
                    break;
                default:
            }
        }
        transactionCategoryParentRepository.saveAll(transactionCategoryParentList);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public User currentUser(String token) {
        Claims claims = jwtUtils.parseToken(token);
        String username = claims.getSubject();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }
}
